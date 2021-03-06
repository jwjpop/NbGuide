package com.cowooding.nbguide.TapPage.MainPage.Board;

/**
 * Created by woojinroom on 2018-04-23.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cowooding.nbguide.R;
import com.cowooding.nbguide.TapPage.MainPage.DocumentActivity;

import java.util.ArrayList;

/**
 * Created by TonyChoi on 2016. 3. 29..
 */
public class CustomAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<InfoClass> InfoArr;
    private ViewHolder holder;

    public CustomAdapter(Context c, ArrayList<InfoClass> array) {
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
        if(v == null) {
            v = mInflater.inflate(R.layout.listview_custom_main, null);
            holder = new ViewHolder();
            holder.title = (TextView)v.findViewById(R.id.tv_title);
            holder.date = (TextView)v.findViewById(R.id.tv_date);
            holder.color = (TextView)v.findViewById(R.id.tv_color);
            holder.number = (TextView)v.findViewById(R.id.tv_number);
            holder.price = (TextView)v.findViewById(R.id.tv_price);
            holder.content = (TextView)v.findViewById(R.id.tv_content);
            holder.user = (TextView)v.findViewById(R.id.tv_user);
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }

        //InfoClass를 생성하여 각 뷰의 포지션에 맞는 데이터를 가져옴
        InfoClass info = InfoArr.get(position);

        //리스트뷰의 아이템에 맞는 String값을 입력
        holder.title.setText(info.title);
        holder.date.setText(info.date);
        holder.color.setText(info.color);
        holder.number.setText(info.number);
        holder.price.setText(info.price);
        holder.content.setText(info.content);
        holder.user.setText(info.user);

        return v;
    }

    //ArrayList Getter And Setter
    public void setArrayList(ArrayList<InfoClass> arrays) {
        this.InfoArr = arrays;
    }

    public ArrayList<InfoClass> getArrayList(){
        return InfoArr;
    }


    /**
     * ViewHolder Class 생성
     */
    private class ViewHolder {
        TextView title;
        TextView date;
        TextView color;
        TextView number;
        TextView price;
        TextView content;
        TextView user;
    }
}

