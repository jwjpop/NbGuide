package com.cowooding.nbguide.TapPage.MyPage;

/**
 * Created by woojin on 2018-07-14.
 */

public class UserClass {
    String id;
    String pw;
    String write;
    String token;

    public UserClass(){}

    public UserClass(String id, String pw,String write,String token){
        this.id = id;
        this.pw = pw;
        this.write = write;
        this.token = token;
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

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
