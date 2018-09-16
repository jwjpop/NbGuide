package com.example.woojinroom.daeran.TapPage.PlayListPage;

/**
 * Created by woojin on 2018-09-11.
 */

public class PlayListClass {
    //String imagepath;
    String music;
    String musician;

    public PlayListClass(){}

    public PlayListClass(String music, String musician) {

        this.music = music;
        this.musician = musician;

    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getMusician() {
        return musician;
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }
}
