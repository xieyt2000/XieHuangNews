package com.java.xieyuntong.backend;

import java.util.ArrayList;

public class StatAPI {
    private static ArrayList<EpidemicStat> allStat = null;
    //Thread for the app to load stat in parallel
    private static Thread requestThread = new Thread(new Runnable() {
        @Override
        public void run() {
            allStat = RequestHandler.requestStat();
        }
    });


    //hidden constructor
    private StatAPI() {
    }


    private static void makeStatReady() {
        if (allStat == null) {
            if (!requestThread.isAlive())
                refreshStat();
            try {
                requestThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //API for frontend client

    public static void refreshStat() {
        requestThread.start();
    }

    public static ArrayList<EpidemicStat> getAllStat() {
        makeStatReady();
        return new ArrayList<>(allStat);
    }

    public static ArrayList<String> getAllCountries() {
        makeStatReady();
        ArrayList<String> countries = new ArrayList<>();
        for (EpidemicStat stat : allStat) {
            if (stat.getProvince() == null)
                countries.add(stat.getCountry());
        }
        return countries;
    }

    public static ArrayList<String> getProvincesByCountry(String country) {
        makeStatReady();
        ArrayList<String> provinces = new ArrayList<>();
        for (EpidemicStat stat : allStat) {
            if (country.equals(stat.getCountry()) && stat.getCity() == null) {
                provinces.add(stat.getProvince());
            }
        }
        return provinces;
    }

    public static EpidemicStat getStatByCountry(String country) {
        makeStatReady();
        for (EpidemicStat stat : allStat) {
            if (country.equals(stat.getCountry()) && stat.getProvince() == null) {
                return stat;
            }
        }
        return null;
    }

    public static EpidemicStat getStatByProvince(String country, String province) {
        makeStatReady();
        for (EpidemicStat stat : allStat) {
            if (country.equals(stat.getCountry()) && province.equals(stat.getProvince()) && stat.getCity() == null) {
                return stat;
            }
        }
        return null;
    }
}
