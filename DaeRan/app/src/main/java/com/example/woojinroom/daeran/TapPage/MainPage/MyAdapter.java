package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woojinroom.daeran.R;

import java.util.ArrayList;

/**
 * Created by woojinroom on 2018-04-05.
 */

public class MyAdapter extends BaseAdapter {

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MyItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tv_color = (TextView) convertView.findViewById(R.id.tv_color);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        MyItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_img.setImageDrawable(myItem.getIcon());
        tv_title.setText(myItem.getTitle());
        tv_date.setText(myItem.getDate());
        tv_color.setText(myItem.getColor());
        tv_price.setText(myItem.getPrice());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */


        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(Drawable img, String title, String date, String color, String price) {

        MyItem mItem = new MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setIcon(img);
        mItem.setTitle(title);
        mItem.setDate(date);
        mItem.setColor(color);
        mItem.setPrice(price);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}