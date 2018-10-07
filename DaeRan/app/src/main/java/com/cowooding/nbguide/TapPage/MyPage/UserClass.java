package com.cowooding.nbguide.TapPage.MyPage;

/**
 * Created by woojin on 2018-07-14.
 */

public class UserClass {
    String id;
    String token;

    public UserClass(){}

    public UserClass(String id,String token){
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {this.token = token;}
}
