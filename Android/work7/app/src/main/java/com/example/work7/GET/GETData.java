package com.example.work7.GET;

public class GETData {
    private String content;
    private String title;
    public GETData(String content,String title){
        this.content=content;
        this.title=title;
    }
    public String getContent(){
        return content;
    }
    public String getTitle(){
        return title;
    }
}
