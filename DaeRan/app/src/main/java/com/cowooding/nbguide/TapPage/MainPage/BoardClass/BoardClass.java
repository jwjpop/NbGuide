package com.cowooding.nbguide.TapPage.MainPage.BoardClass;

public class BoardClass { // 모든 내용이 들어 있는 보드 클래스

    String title;
    String date;
    String color;
    String number;
    String price;
    String content;
    String user;

    public BoardClass(){}

    public BoardClass(String title, String date, String color,String number,String price,String content,String user) {

        this.title = title;
        this.date = date;
        this.color = color;
        this.number = number;
        this.price = price;
        this.content = content;
        this.user = user;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
