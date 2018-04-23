package com.example.woojinroom.daeran.TapPage.MainPage;

import android.graphics.drawable.Drawable;

/**
 * Created by woojinroom on 2018-04-05.
 */

public class MyItem {

    private Drawable icon;
    private String title;
    private String date;
    private String color;
    private String price;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
