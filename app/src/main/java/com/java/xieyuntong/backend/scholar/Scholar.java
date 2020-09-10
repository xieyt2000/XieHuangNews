package com.java.xieyuntong.backend.scholar;

import java.net.URL;

public class Scholar {

    public static class Indices {
        final public double activity;
        final public int citations;
        final public int gindex;
        final public int hindex;
        final public double newStar;
        final public double risingStar;
        final public double sociability;

        public Indices(double activity, int citations, int gindex, int hindex, double newStar, double risingStar, double sociability) {
            this.activity = activity;
            this.citations = citations;
            this.gindex = gindex;
            this.hindex = hindex;
            this.newStar = newStar;
            this.risingStar = risingStar;
            this.sociability = sociability;
        }
    }


    //class variables
    final private String name;
    final private URL imgURL;
    final private Indices indices;
    final private String bio;
    final private String affiliation;
    final private String education;
    final private boolean passedAway;


    // constructor

    public Scholar(String name, URL imgURL, Indices indices, String bio,
                   String affiliation, String education, boolean passedAway) {
        this.name = name;
        this.imgURL = imgURL;
        this.indices = indices;
        this.bio = bio;
        this.affiliation = affiliation;
        this.education = education;
        this.passedAway = passedAway;
    }


    // getter

    public String getName() {
        return name;
    }

    public URL getImgURL() {
        return imgURL;
    }

    public Indices getIndices() {
        return indices;
    }

    public String getBio() {
        return bio;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getEducation() {
        return education;
    }

    public boolean isPassedAway() {
        return passedAway;
    }
}
