package com.java.xieyuntong.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

class RequestHandler {

    //construct URL string for "GET" method with parameters
    static String constructURL(String urlStr, final Map<String, String> paraMap) {
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
    static String httpGet(String URLStr, final Map<String, String> parameters) {
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

    static ArrayList<NewsPiece> requestNews(NewsPiece.NewsType type, int page, int size) {
        LinkedHashMap<String, String> para = new LinkedHashMap<>();
        para.put("type", type.toString());
        para.put("page", Integer.toString(page));
        para.put("size", Integer.toString(size));
        String newsURLStr = "https://covid-dashboard.aminer.cn/api/events/list";
        String jsonStr = httpGet(newsURLStr, para);
        ArrayList<NewsPiece> res = new ArrayList<>();
        try {
            assert jsonStr != null;
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray jsonDataArr = jsonObj.getJSONArray("data");
            for (int i = 0; i < jsonDataArr.length(); i++) {
                JSONObject jsonNewsPieceObj = jsonDataArr.getJSONObject(i);
                res.add(new NewsPiece(type, jsonNewsPieceObj.getString("date"),
                        jsonNewsPieceObj.getString("source"), jsonNewsPieceObj.getString("title"),
                        jsonNewsPieceObj.getString("content")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

}

