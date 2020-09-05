package com.java.xieyuntong.backend;

import java.util.ArrayList;

public class StatAPI {
    private static ArrayList<EpidemicStat> allStat = null;

    public static void refreshStat() {
        Thread netThread = new Thread(new Runnable() {
            @Override
            public void run() {
                allStat = RequestHandler.requestStat();
            }
        });
        netThread.start();
        try {
            netThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<EpidemicStat> getAllStat() {
        if (allStat != null) return allStat;
        refreshStat();
        return allStat;
    }
}
