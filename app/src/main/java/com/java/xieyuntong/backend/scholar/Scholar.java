package com.java.xieyuntong.backend.scholar;

import android.graphics.Bitmap;

public class Scholar {

    public static class Indices {
        final public int activity;
        final public int citations;
        final public int gindex;
        final public int hindex;
        final public int newStar;
        final public int risingStar;
        final public int sociability;

        public Indices(int activity, int citations, int gindex, int hindex, int newStar, int risingStar, int sociability) {
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
    final private Bitmap img;
    final private Indices indices;
    final private String bio;
    final private String affiliation;
    final private String education;
    final private boolean passedAway;


    // constructor

    public Scholar(String name, Bitmap img, Indices indices, String bio,
                   String affiliation, String education, boolean passedAway) {
        this.name = name;
        this.img = img;
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

    public Bitmap getImg() {
        return img;
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
