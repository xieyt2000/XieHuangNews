package com.java.xieyuntong.backend.scholar;

import com.java.xieyuntong.backend.RequestHandler;

import java.util.ArrayList;

public class ScholarAPI {
    private static ArrayList<Scholar> scholars = null;
    private static Thread requestThread = new Thread(new Runnable() {
        @Override
        public void run() {
            scholars = RequestHandler.requestScholars();
        }
    });
    private static boolean threadStarted = false;

    private static void makeScholarReady() {
        if (scholars == null) {
            refreshScholar();
            try {
                requestThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //API for frontend client

    public static void refreshScholar() {
        if (!threadStarted) {
            requestThread.start();
            threadStarted = true;
        }
    }

    public static ArrayList<Scholar> getAllScholars() {
        makeScholarReady();
        return new ArrayList<>(scholars);
    }
}
