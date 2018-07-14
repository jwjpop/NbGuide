package com.example.woojinroom.daeran.TapPage.MyPage;

/**
 * Created by woojin on 2018-07-14.
 */

public class UserClass {
    String id;
    String pw;

    public UserClass(){}

    public UserClass(String id, String pw){
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
