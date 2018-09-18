package com.example.woojinroom.nbguide.TapPage.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woojinroom.nbguide.R;

import java.util.ArrayList;

/**
 * Created by woojin on 2018-08-10.
 */

public class ChatListCustomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<String> ChatList;
    private ViewHolder holder;
    public String sender;

    public ChatListCustomAdapter(Context c, ArrayList<String> array, String sender) {
        mInflater = LayoutInflater.from(c);
        ChatList = array;
        this.sender = sender;
    }

    @Override
    public int getCount() {
        return ChatList.size();
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
        v = mInflater.inflate(R.layout.listview_custom_chatlist, null);
        holder.user = (TextView) v.findViewById(R.id.tv_user_id);

        String chat = ChatList.get(position);

        //리스트뷰의 아이템에 맞는 String값을 입력
        holder.user.setText(chat);

        return v;
    }

    private class ViewHolder {
        TextView user;
    }

}
