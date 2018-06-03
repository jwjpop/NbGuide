package com.example.woojinroom.daeran.TapPage.MainPage.BoardClass;

public class BoardClass {

    String title;
    String date;
    String color;
    String number;
    String price;

    public BoardClass(String title, String date, String color,String number,String price) {

        this.title = title;
        this.date = date;
        this.color = color;
        this.number = number;
        this.price = price;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
