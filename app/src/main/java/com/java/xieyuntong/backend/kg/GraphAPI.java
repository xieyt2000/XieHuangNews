package com.java.xieyuntong.backend.kg;

import com.java.xieyuntong.backend.RequestHandler;

public class GraphAPI {
    static public Entity searchEntity(String query) {
        return RequestHandler.requestGraphEntry(query);
    }
}
