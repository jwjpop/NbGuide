package com.cowooding.nbguide.DB;

/**
 * Created by woojinroom on 2018-04-23.
 */

import com.cowooding.nbguide.TapPage.MainPage.Board.BoardClass;

/**
 * Created by TonyChoi on 2016. 3. 29..
 */
public class InfoClass { // 글에 뿌려줄 내용만 가지고 있는 인포 클래스 listview

    public String title;
    public String date;
    public String color;
    public String number;
    public String price;
    public String content;
    public String user;

    //생성자
    public InfoClass(){}

    public InfoClass(BoardClass boardClass) {

        this.title = boardClass.getTitle();
        this.date = boardClass.getDate();
        this.color = boardClass.getColor();
        this.number = boardClass.getNumber();
        this.price = boardClass.getPrice();
        this.content = boardClass.getContent();
        this.user = boardClass.getUser();
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

