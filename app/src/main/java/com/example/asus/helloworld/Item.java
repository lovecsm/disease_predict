package com.example.asus.helloworld;



public class Item {
    private String title;
    private String content;
    private int imgId;
    private String imgUrl;
    private String essayUrl;
    public Item(String title, String content, int imgId){
        this.imgId = imgId;
        this.title = title;
        this.content = content;
    }
    public Item(String title, String content, String imgUrl, String essayUrl){
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.essayUrl = essayUrl;
    }
    public String getTitle() {
        return title;
    }
    public int getImgId() {
        return imgId;
    }
    public String getContent(){ return content; }
}
