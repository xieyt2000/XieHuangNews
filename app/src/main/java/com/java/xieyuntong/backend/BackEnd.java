package com.java.xieyuntong.backend;

import android.app.Activity;

import java.io.File;

public class BackEnd {
    static File rootFileDir;


    //frontend call this to initialize backend
    public static void initialize(Activity activity) {
        rootFileDir = activity.getFilesDir();
        NewsHistory.HistoryFile = new File(rootFileDir, "history.txt");
    }
}
