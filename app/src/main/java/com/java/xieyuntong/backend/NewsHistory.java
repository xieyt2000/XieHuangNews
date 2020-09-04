package com.java.xieyuntong.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class NewsHistory {

    static File HistoryFile;

    static void clear() {
        HistoryFile.delete();
    }

    static void saveNews(NewsPiece news) {
        try (PrintStream ps = new PrintStream(new FileOutputStream(HistoryFile, true))) {
            ps.println(news);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<NewsPiece> readHistory() {
        ArrayList<NewsPiece> rdList = new ArrayList<>();
        try (Scanner fileIn = new Scanner(HistoryFile)) {
            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine();
                NewsPiece news = new NewsPiece(line);
                rdList.add(news);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<NewsPiece> res = new ArrayList<>();
        for (int i = rdList.size() - 1; i > 0; i--) {
            NewsPiece news = rdList.get(i);
            if (!res.contains(news))
                res.add(news);
        }
        return res;
    }
}
