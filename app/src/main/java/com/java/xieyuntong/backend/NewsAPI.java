package com.java.xieyuntong.backend;

import java.util.ArrayList;

public class NewsAPI {
    RequestHandler requestHandler = new RequestHandler();
    //member variable
    private ArrayList<ArrayList<NewsPiece>> newsPagesList = new ArrayList<>();
    private int size = 20;
    private NewsPiece.NewsType type = NewsPiece.NewsType.NEWS;


    //API for frontend client

    public ArrayList<NewsPiece> getCurPage() {
        return new ArrayList<>(newsPagesList.get(newsPagesList.size() - 1));
    }

    public ArrayList<NewsPiece> getNextPage() {
        Thread netThread = new Thread(new Runnable() {
            @Override
            public void run() {
                newsPagesList.add(requestHandler.requestNews(type, newsPagesList.size() + 1, size));
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

    public void resetPageSize(int size) {
        this.size = size;
        reset();
    }

    public void saveNews(NewsPiece news) {

    }

    public ArrayList<NewsPiece> search(String query) {
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
    public void read(final NewsPiece newsPiece) {
        newsPiece.read();
        saveNews(newsPiece);
    }

    public void reset() {
        newsPagesList.clear();
    }

    public void setType(NewsPiece.NewsType type) {
        reset();
        this.type = type;
    }

    public ArrayList<NewsPiece> refresh() {
        reset();
        return getNextPage();
    }

}
