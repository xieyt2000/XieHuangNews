package com.java.xieyuntong.backend.news;

import androidx.annotation.NonNull;

import com.java.xieyuntong.backend.BasicFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NewsPiece {

    private static SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
    private static SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public enum NewsType {
        NEWS("news"), PAPER("paper"), EVENT("event");
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
    private final Date time;
    private final String source;
    private final String title;
    private final String content;
    private boolean haveRead = false;


    //constructor

    public NewsPiece(NewsType type, String timeStr, String source, String title, String content, String ID) {
        Date time1;
        this.type = type;
        try {
            time1 = inputFormat.parse(timeStr);
        } catch (ParseException e) {
            time1 = null;
            e.printStackTrace();
        }
        this.time = time1;
        this.source = source;
        this.title = title.replace("\n", "");
        this.content = content;
        this.ID = ID;
    }

    protected NewsPiece(String str) {
        Date time1;
        String[] strList = str.split("###");
        this.type = NewsType.fromString(strList[0]);
        try {
            time1 = inputFormat.parse(strList[1]);
        } catch (ParseException e) {
            time1 = null;
            e.printStackTrace();
        }
        this.time = time1;
        this.source = strList[2];
        this.title = strList[3];
        this.content = strList[4];
        this.ID = strList[5];
    }


    //basic override method

    @NonNull
    @Override
    public String toString() {
        return String.join("###", type.toString(), inputFormat.format(time), source, title, content, ID);
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

    public Date getTime() {
        return time;
    }

    public String getTimeStr() {
        return outFormat.format(time);
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

    public String getTitleAbstract() {
        return BasicFunction.getPrefixAbs(title, 84);
    }

    public boolean getHaveRead() {
        return haveRead;
    }
}
