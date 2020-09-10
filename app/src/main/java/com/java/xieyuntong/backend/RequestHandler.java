package com.java.xieyuntong.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.java.xieyuntong.backend.kg.Entity;
import com.java.xieyuntong.backend.news.NewsPiece;
import com.java.xieyuntong.backend.scholar.Scholar;
import com.java.xieyuntong.backend.stat.EpidemicStat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHandler {

    // constants
    static final String NEWS_URL_STR = "https://covid-dashboard.aminer.cn/api/events/list";
    static final String STAT_URL_STR = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
    static final String GRAPH_URL_STR = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery";
    static final String SCHOLAR_URL_STR = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";

    //construct URL string for "GET" method with parameters
    static String constructURL(String urlStr, @Nullable final Map<String, String> paraMap) {
        if (paraMap == null)
            return urlStr;
        StringBuilder builder = new StringBuilder(urlStr + "?");
        for (Map.Entry<String, String> parameter : paraMap.entrySet()) {
            builder.append(parameter.getKey());
            builder.append("=");
            builder.append(parameter.getValue());
            builder.append("&");
        }
        return builder.substring(0, builder.length() - 1);
    }

    //http "GET" return string
    static String httpGet(String URLStr, @Nullable final Map<String, String> parameters) {
        try {
            URL url = new URL(constructURL(URLStr, parameters));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<NewsPiece> requestNews(NewsPiece.NewsType type, int page, int size) {
        LinkedHashMap<String, String> para = new LinkedHashMap<>();
        para.put("type", type.toString());
        para.put("page", Integer.toString(page));
        para.put("size", Integer.toString(size));
        String jsonStr = httpGet(NEWS_URL_STR, para);
        ArrayList<NewsPiece> res = new ArrayList<>();
        try {
            assert jsonStr != null;
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray jsonDataArr = jsonObj.getJSONArray("data");
            for (int i = 0; i < jsonDataArr.length(); i++) {
                JSONObject jsonNewsPieceObj = jsonDataArr.getJSONObject(i);
                res.add(new NewsPiece(type, jsonNewsPieceObj.getString("date"),
                        jsonNewsPieceObj.getString("source"), jsonNewsPieceObj.getString("title"),
                        jsonNewsPieceObj.getString("content"), jsonNewsPieceObj.getString("_id")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<EpidemicStat> requestStat() {
        String jsonStr = httpGet(STAT_URL_STR, null);
        ArrayList<EpidemicStat> res = new ArrayList<>();
        try {
            JSONObject jsonMap = new JSONObject(jsonStr);
            Iterator<String> keys = jsonMap.keys();
            while (keys.hasNext()) {
                String region = keys.next();
                JSONObject jsonStat = jsonMap.getJSONObject(region);
                String dateString = jsonStat.getString("begin");
                JSONArray jsonDataArr = jsonStat.getJSONArray("data");
                ArrayList<ArrayList<Integer>> figures = new ArrayList<>();
                for (int i = 0; i < jsonDataArr.length(); i++) {
                    JSONArray jsonFigure = jsonDataArr.getJSONArray(i);
                    ArrayList<Integer> figureArr = new ArrayList<>();
                    for (int j = 0; j < jsonFigure.length(); j++) {
                        try {
                            figureArr.add(Integer.valueOf(jsonFigure.getString(j)));
                        } catch (NumberFormatException e) {
                            figureArr.add(null);
                        }
                    }
                    figures.add(figureArr);
                }
                res.add(new EpidemicStat(region, dateString, figures));
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Entity requestGraphEntry(String query) {
        LinkedHashMap<String, String> para = new LinkedHashMap<>();
        para.put("entity", query);
        String jsonStr = httpGet(GRAPH_URL_STR, para);
        try {
            assert jsonStr != null;
            JSONObject jsonEntity = new JSONObject(jsonStr).
                    getJSONArray("data").getJSONObject(0);
            String label = jsonEntity.getString("label");
            URL imgURL = new URL(jsonEntity.getString("img"));
            Bitmap img = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
            JSONObject jsonInfo = jsonEntity.getJSONObject("abstractInfo");
            String description = jsonInfo.getString("baidu");
            if (description.equals(""))
                description = jsonInfo.getString("zhwiki");
            if (description.equals(""))
                description = jsonInfo.getString("enwiki");
            JSONObject jsonCOVID = jsonInfo.getJSONObject("COVID");
            LinkedHashMap<String, String> properties = new LinkedHashMap<>();
            JSONObject jsonProperties = jsonCOVID.getJSONObject("properties");
            Iterator<String> propertyKeys = jsonProperties.keys();
            while (propertyKeys.hasNext()) {
                String title = propertyKeys.next();
                String content = jsonProperties.getString(title);
                if (content.startsWith("[") && content.endsWith("]")) {
                    JSONArray jsonContentArr = jsonProperties.getJSONArray(title);
                    StringBuilder contentBuilder = new StringBuilder();
                    for (int i = 0; i < jsonContentArr.length(); i++) {
                        contentBuilder.append(jsonContentArr.get(i));
                        contentBuilder.append('\n');
                    }
                    contentBuilder.setLength(contentBuilder.length() - 1);    //delete last \n
                    content = contentBuilder.toString();
                }
                properties.put(title, content);
            }
            ArrayList<Entity.Relation> relations = new ArrayList<>();
            JSONArray jsonRelations = jsonCOVID.getJSONArray("relations");
            for (int i = 0; i < jsonRelations.length(); i++) {
                JSONObject jsonRelation = jsonRelations.getJSONObject(i);
                relations.add(new Entity.Relation(jsonRelation.getString("relation"),
                        jsonRelation.getString("label"), jsonRelation.getBoolean("forward")));
            }
            return new Entity(label, img, description, properties, relations);
        } catch (JSONException | IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Scholar> requestScholars() {
        String jsonStr = httpGet(SCHOLAR_URL_STR, null);
        ArrayList<Scholar> scholars = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray jsonData = jsonObj.getJSONArray("data");
            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject jsonScholar = jsonData.getJSONObject(i);
                URL imgURL = new URL(jsonScholar.getString("avatar"));
                JSONObject jsonInd = jsonScholar.getJSONObject("indices");
                Scholar.Indices indices = new Scholar.Indices(jsonInd.getDouble("activity"),
                        jsonInd.getInt("citations"), jsonInd.getInt("gindex"),
                        jsonInd.getInt("hindex"), jsonInd.getDouble("newStar"),
                        jsonInd.getDouble("risingStar"), jsonInd.getDouble("sociability"));
                String name = jsonScholar.optString("name_zh");
                if (name.equals(""))
                    name = jsonScholar.getString("name");
                JSONObject jsonProfile = jsonScholar.getJSONObject("profile");
                String bio = jsonProfile.getString("bio");
                String affiliation = jsonProfile.optString("affiliation_zh");
                if (affiliation.equals(""))
                    affiliation = jsonProfile.optString("affiliation");
                String education = jsonProfile.optString("edu");
                boolean passed = jsonScholar.getBoolean("is_passedaway");
                scholars.add(new Scholar(name, imgURL, indices, bio, affiliation, education, passed));
            }
        } catch (JSONException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return scholars;
    }

    static public Bitmap getImage(final String urlStr) {
        final Bitmap[] ret = {null};
        Thread netThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL imgURL = new URL(urlStr);
                    HttpURLConnection connection = (HttpURLConnection) imgURL.openConnection();
                    connection.setConnectTimeout(1000);
                    ret[0] = BitmapFactory.decodeStream(connection.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        netThread.start();
        try {
            netThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ret[0];
    }
}

