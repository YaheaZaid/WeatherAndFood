package com.example.weatherapp;

public class MainModel {
    Integer langImg;
    String langName;

    public MainModel(Integer langImg, String langName){
        this.langImg = langImg;
        this.langName = langName;
    }
    public Integer getLangImg(){
        return langImg;
    }
    public String getLangName(){
        return langName;


    }
}
