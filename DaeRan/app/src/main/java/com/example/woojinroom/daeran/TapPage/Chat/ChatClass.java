package com.example.woojinroom.daeran.TapPage.Chat;

/**
 * Created by woojin on 2018-08-10.
 */

public class ChatClass {
    public String user;
    public String date;
    public String text;

    public ChatClass(){}

    public ChatClass(String user,String date,String text){
        this.user=user;
        this.date=date;
        this.text=text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
