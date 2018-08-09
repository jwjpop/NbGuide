package com.example.woojinroom.daeran.TapPage.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.woojinroom.daeran.R;

import java.util.ArrayList;

/**
 * Created by woojin on 2018-08-10.
 */

public class ChatCustomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<ChatClass> ChatArr;
    private ViewHolder holder;

    public ChatCustomAdapter(Context c, ArrayList<ChatClass> array) {
        mInflater = LayoutInflater.from(c);
        ChatArr = array;
    }

    @Override
    public int getCount() {
        return ChatArr.size();
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

        if (v == null) {
            holder = new ViewHolder();
            v = mInflater.inflate(R.layout.listview_custom_chat, null);
            holder.user = (TextView)v.findViewById(R.id.tv_user_id);
            holder.date = (TextView)v.findViewById(R.id.tv_user_date);
            holder.text = (TextView)v.findViewById(R.id.tv_user_text);

            v.setTag(holder);
        } else {
            holder = (ViewHolder)v.getTag();
        }

        ChatClass chat = ChatArr.get(position);

        //리스트뷰의 아이템에 맞는 String값을 입력
        holder.user.setText(chat.user);
        holder.date.setText(chat.date);
        holder.text.setText(chat.text);

        return v;
    }

    private class ViewHolder {
        TextView user;
        TextView date;
        TextView text;
    }
}
