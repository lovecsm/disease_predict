package com.example.asus.helloworld;

/**
 * Created by ASUS on 2018/6/27.
 */

public class HealthSecondItem extends Item {
    private String imgUrl;
    private String essayUrl;
    public HealthSecondItem(String title, String content, String imgUrl, String essayUrl){
        super(title, content, imgUrl, essayUrl);
        this.imgUrl = imgUrl;
        this.essayUrl = essayUrl;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public String getEssayUrl(){return essayUrl;}
}
