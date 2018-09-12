package com.example.woojinroom.daeran.TapPage.PlayListPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woojinroom.daeran.R;

import java.util.ArrayList;

/**
 * Created by woojin on 2018-09-12.
 */

public class PlayListCustomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<PlayListClass> InfoArr;
    private ViewHolder holder;

    public PlayListCustomAdapter(Context c, ArrayList<PlayListClass> array) {
        mInflater = LayoutInflater.from(c);
        InfoArr = array;
    }

    @Override
    public int getCount() {
        return InfoArr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;


        holder = new ViewHolder();
        v = mInflater.inflate(R.layout.listview_custom_playlist, null);
        holder.image = (ImageView) v.findViewById(R.id.iv_img);
        holder.music = (TextView)v.findViewById(R.id.tv_music);
        holder.musician = (TextView)v.findViewById(R.id.tv_musician);




        //InfoClass를 생성하여 각 뷰의 포지션에 맞는 데이터를 가져옴
        PlayListClass info = InfoArr.get(position);

        //리스트뷰의 아이템에 맞는 String값을 입력
        //holder.image.setText(info.imagepath);
        holder.music.setText(info.music);
        holder.musician.setText(info.musician);


        return v;
    }

    //ArrayList Getter And Setter
    public void setArrayList(ArrayList<PlayListClass> arrays) {
        this.InfoArr = arrays;
    }

    public ArrayList<PlayListClass> getArrayList(){
        return InfoArr;
    }


    /**
     * ViewHolder Class 생성
     */
    private class ViewHolder {
        ImageView image;
        TextView music;
        TextView musician;
    }
}
