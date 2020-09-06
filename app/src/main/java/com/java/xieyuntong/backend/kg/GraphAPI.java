package com.java.xieyuntong.backend.kg;

import com.java.xieyuntong.backend.RequestHandler;

public class GraphAPI {
    static public Entity searchEntity(final String query) {
        final Entity[] res = {null};
        Thread netThread = new Thread(new Runnable() {
            @Override
            public void run() {
                res[0] = RequestHandler.requestGraphEntry(query);
            }
        });
        netThread.start();
        try {
            netThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res[0];
    }
}
