package com.example.work6.message;

public class messData {
    private String title;
    private String data;
    private int image;
    public messData(String data,String title,int image){
        this.data=data;
        this.image=image;
        this.title=title;
    }
    public String getData(){
    return data;
    }
    public int getImage(){
        return image;
    }
    public String getTitle(){
        return title;
    }
}
