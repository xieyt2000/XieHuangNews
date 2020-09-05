package com.java.xieyuntong.backend;

import androidx.annotation.NonNull;


public class NewsPiece {

    public enum NewsType {
        NEWS("news"), PAPER("paper");
        private final String text;

        NewsType(String text) {
            this.text = text;
        }

        static public NewsType fromString(String str) {
            if (str.equals("news")) return NEWS;
            else return PAPER;
        }

        @NonNull
        @Override
        public String toString() {
            return text;
        }
    }


    // class variables

    private final String ID;   //ID from aminer
    private final NewsType type;
    private final String time;
    private final String source;
    private final String title;
    private final String content;
    private boolean haveRead = false;


    //constructor

    NewsPiece(NewsType type, String time, String source, String title, String content, String ID) {
        this.type = type;
        this.time = time;
        this.source = source;
        this.title = title;
        this.content = content;
        this.ID = ID;
    }

    NewsPiece(String str) {
        String[] strList = str.split("###");
        this.type = NewsType.fromString(strList[0]);
        this.time = strList[1];
        this.source = strList[2];
        this.title = strList[3];
        this.content = strList[4];
        this.ID = strList[5];
    }


    //basic override method

    @NonNull
    @Override
    public String toString() {
        return String.join("###", type.toString(), time, source, title, content, ID);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NewsPiece)) return false;
        NewsPiece cmpNews = (NewsPiece) obj;
        return cmpNews.ID.equals(this.ID);
    }


    //setter

    public void read() {
        haveRead = true;
    }

    public void resetRead() {
        haveRead = false;
    }


    // getter

    String getID() {
        return ID;
    }

    public NewsType getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getSource() {
        return source;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public boolean getHaveRead() {
        return haveRead;
    }
}
