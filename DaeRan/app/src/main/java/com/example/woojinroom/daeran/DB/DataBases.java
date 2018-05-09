package com.example.woojinroom.daeran.DB;

import android.provider.BaseColumns;

/**
 * Created by woojinroom on 2018-04-23.
 */

public class DataBases {
    //데이터베이스 호출 시 사용될 생성자
    public static final class CreateDB implements BaseColumns {
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String COLOR = "color";
        public static final String NUMBER = "number";
        public static final String PRICE = "price";
        public static final String _TABLENAME = "text4"; // 수정하면 새로운 테이블 생성 됨
        public static final String _CREATE =
                "create table " + _TABLENAME + "("
                        + _ID + " integer primary key autoincrement, "
                        + TITLE + " text not null , "
                        + DATE + " text not null , "
                        + COLOR + " text not null , "
                        + NUMBER + " text not null , "
                        + PRICE + " text not null ); ";
    }
}

