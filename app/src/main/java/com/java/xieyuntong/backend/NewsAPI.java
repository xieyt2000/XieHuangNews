package com.java.xieyuntong.backend;

import java.util.ArrayList;

public class NewsAPI {
    //member variable
    static private ArrayList<ArrayList<NewsPiece>> newsPagesList = new ArrayList<>();
    static private int size = 20;
    static private NewsPiece.NewsType type = NewsPiece.NewsType.NEWS;


    //API for frontend client

    static public ArrayList<NewsPiece> getCurPage() {
        return new ArrayList<>(newsPagesList.get(newsPagesList.size() - 1));
    }

    static public ArrayList<NewsPiece> getNextPage() {
        Thread netThread = new Thread(new Runnable() {
            @Override
            public void run() {
                newsPagesList.add(RequestHandler.requestNews(type, newsPagesList.size() + 1, size));
            }
        });
        netThread.start();
        try {
            netThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getCurPage();
    }

    static public void resetPageSize(int size) {
        NewsAPI.size = size;
        reset();
    }


    static public ArrayList<NewsPiece> search(String query) {
        ArrayList<NewsPiece> res = new ArrayList<>();
        for (ArrayList<NewsPiece> newsPage : newsPagesList) {
            for (NewsPiece newsPiece : newsPage) {
                if (newsPiece.getTitle().contains(query) || newsPiece.getContent().contains(query))
                    res.add(newsPiece);
            }
        }
        return res;
    }

    //action to take when client read a news
    static public void read(final NewsPiece newsPiece) {
        newsPiece.read();
        NewsHistory.saveNews(newsPiece);
    }

    static public void reset() {
        newsPagesList.clear();
    }

    static public void setType(NewsPiece.NewsType type) {
        if (type.equals(NewsAPI.type)) {
            return;
        }
        reset();
        NewsAPI.type = type;
    }

    static public ArrayList<NewsPiece> refresh() {
        reset();
        return getNextPage();
    }

    static public ArrayList<NewsPiece> getHistory() {
        return NewsHistory.readHistory();
    }

    static public void clearHistory() {
        NewsHistory.clear();
    }
}
