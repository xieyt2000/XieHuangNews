package com.java.xieyuntong.backend;

import android.app.Activity;

import com.java.xieyuntong.backend.cluster.ClusterAPI;
import com.java.xieyuntong.backend.scholar.ScholarAPI;

import java.io.File;
import java.io.IOException;

public class BackEnd {
    static File rootFileDir;


    //frontend call this to initialize backend
    public static void initialize(Activity activity) {
        rootFileDir = activity.getFilesDir();
        NewsHistory.HistoryFile = new File(rootFileDir, "history.txt");
        ScholarAPI.refreshScholar();
        StatAPI.refreshStat();
        try {
            ClusterAPI.loadCluster(activity.getAssets().open("cluster.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
