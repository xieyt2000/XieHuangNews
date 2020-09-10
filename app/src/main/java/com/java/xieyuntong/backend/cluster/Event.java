package com.java.xieyuntong.backend.cluster;

import com.java.xieyuntong.backend.NewsPiece;

public class Event extends NewsPiece {

    private final int clusterID;


    //constructor

    Event(String timeStr, String source, String title, int clusterID, String keywords) {
        super(NewsType.EVENT, timeStr, source, title, "", "");
        this.clusterID = clusterID;
    }


    //getter

    public String getKeywords() {
        return ClusterAPI.getClusterKeywords().get(clusterID);
    }

    public int getClusterID() {
        return clusterID;
    }
}
