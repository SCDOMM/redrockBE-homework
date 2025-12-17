package com.example.work7.GET;

import java.util.List;

public class jsonData {
    List<DD> data;
    int errCode;
    String errMsg;
    static class DD{
        String desc;
        int id;
        String imagePath;
        int isVisible;
        int order;
        String title;
        int type;
        String url;
    }
}
