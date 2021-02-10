package com.example.advertisingapp;

public class MyData {

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    int image;
    String text;


    public MyData(int image, String text){
        this.image = image;
        this.text = text;
    }
}
