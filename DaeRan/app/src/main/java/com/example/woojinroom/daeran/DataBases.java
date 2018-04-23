package com.example.woojinroom.daeran;

import android.provider.BaseColumns;

/**
 * Created by woojinroom on 2018-04-23.
 */

public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String Title = "title";
        public static final String Color = "color";
        public static final String Price = "price";
        public static final String Content = "content";
        public static final String _TABLENAME = "text";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +_ID+" integer primary key autoincrement, "
                        +Title+" text not null , "
                        +Color+" text not null , "
                        +Price+" text not null , "
                        +Content+" text not null );";
    }
}

