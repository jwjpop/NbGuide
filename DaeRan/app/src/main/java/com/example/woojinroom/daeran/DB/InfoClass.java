package com.example.woojinroom.daeran.DB;

/**
 * Created by woojinroom on 2018-04-23.
 */
/**
 * Created by TonyChoi on 2016. 3. 29..
 */
public class InfoClass {

    public int _id;
    public String title;
    public String date;
    public String color;
    public String number;
    public String price;

    //생성자
    public InfoClass(){}

    /**
     * 실질적으로 값을 입력할 때 사용되는 생성자(getter and setter)
     * @param _id       테이블 아이디
     * @param title
     * @param date
     * @param color
     * @param number
     * @param price
     */
    public InfoClass(int _id, String title, String date, String color,String number,String price) {
        this._id = _id;
        this.title = title;
        this.date = date;
        this.color = color;
        this.number = number;
        this.price = price;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
/*  public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }*/
}

