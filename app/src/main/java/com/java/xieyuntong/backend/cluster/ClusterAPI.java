package com.java.xieyuntong.backend.cluster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class ClusterAPI {
    private static final ArrayList<ArrayList<Event>> eventListByCluster = new ArrayList<>();
    private static final ArrayList<String> clusterKeywords = new ArrayList<>();

    public static final int CLUSTER_NUM = 10;

    public static void loadCluster(InputStream clusterStream) {
        for (int i = 0; i < CLUSTER_NUM; i++) {
            eventListByCluster.add(new ArrayList<Event>());
            clusterKeywords.add(null);
        }
        String jsonString = new Scanner(clusterStream).nextLine();
        try {
            JSONArray jsonArr = new JSONArray(jsonString);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonEvent = jsonArr.getJSONObject(i);
                int clusterId = jsonEvent.getInt("cluster");
                String keyword = jsonEvent.getString("keywords");
                eventListByCluster.get(clusterId).add(new Event(jsonEvent.getString("date"), jsonEvent.getString("source"),
                        jsonEvent.getString("title"), clusterId, keyword));
                clusterKeywords.set(clusterId, keyword);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getClusterKeywords() {
        return clusterKeywords;
    }

    public static ArrayList<Event> getEventsByCluster(int clusterID) {
        return eventListByCluster.get(clusterID);
    }
}
