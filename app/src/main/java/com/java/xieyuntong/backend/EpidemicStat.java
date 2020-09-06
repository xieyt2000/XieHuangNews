package com.java.xieyuntong.backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class EpidemicStat {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static class Figure {
        public final Integer confirmed, suspected, cured, dead, severe, risk, inc24;

        public Figure(Integer confirmed, Integer suspected, Integer cured,
                      Integer dead, Integer severe, Integer risk, Integer inc24) {
            this.confirmed = confirmed;
            this.suspected = suspected;
            this.cured = cured;
            this.dead = dead;
            this.severe = severe;
            this.risk = risk;
            this.inc24 = inc24;
        }

        public Figure(ArrayList<Integer> figureArr) {
            this(figureArr.get(0), figureArr.get(1), figureArr.get(2), figureArr.get(3),
                    figureArr.get(4), figureArr.get(5), figureArr.get(6));
        }
    }


    // class variables
    private final String country;
    private final String province;
    private final String city;
    private final Date beginDate;
    final private ArrayList<Figure> figures;


    // constructor
    public EpidemicStat(String country, String province, String city, Date beginDate, ArrayList<Figure> figures) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.beginDate = beginDate;
        this.figures = figures;
    }

    public EpidemicStat(String regionStr, String dateStr, ArrayList<ArrayList<Integer>> figuresArr) {
        // region
        ArrayList<String> regionArr = new ArrayList<>(Arrays.asList(regionStr.split("\\|")));
        for (int i = regionArr.size(); i < 3; i++) {
            regionArr.add(null);
        }
        this.country = regionArr.get(0);
        this.province = regionArr.get(1);
        this.city = regionArr.get(2);
        // date
        Date tmpDate;
        try {
            tmpDate = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            tmpDate = null;
        }
        this.beginDate = tmpDate;
        // figure
        figures = new ArrayList<>();
        for (ArrayList<Integer> figureArr : figuresArr) {
            figures.add(new Figure(figureArr));
        }
    }


    // getter
    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public ArrayList<Figure> getFigures() {
        return this.figures;
    }

}
