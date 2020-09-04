package com.java.xieyuntong.backend;

import androidx.annotation.NonNull;


public class NewsPiece {

    private final NewsType type;
    private final String time;
    private final String source;
    private final String title;
    private final String content;
    private boolean haveRead = false;

    //constructor
    NewsPiece(NewsType type, String date, String source, String title, String content) {
        this.type = type;
        this.time = date;
        this.source = source;
        this.title = title;
        this.content = content;
    }

    // method to access class variables
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

    // method to change news status
    void read() {
        haveRead = true;
    }

    void resetRead() {
        haveRead = false;
    }

    // class variables
    public enum NewsType {
        NEWS("news"), PAPER("paper");
        private final String text;

        NewsType(String text) {
            this.text = text;
        }

        @NonNull
        @Override
        public String toString() {
            return text;
        }
    }
}
