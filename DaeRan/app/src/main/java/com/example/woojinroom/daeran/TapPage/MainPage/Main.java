package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.woojinroom.daeran.R;
import com.example.woojinroom.daeran.TapPage.MainPage.WritePage.WriteActivity;


/**
 * Created by woojinroom on 2018-01-31.
 */

public class Main extends Fragment {
    ListView mListView;
    ImageButton mImageButton;
    public static Main newInstance() {
        return new Main();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_main, container, false); // 여기서 UI를 생성해서 View를 return
        mListView = (ListView) view.findViewById(R.id.listView);
        mImageButton = (ImageButton)view.findViewById(R.id.iamgebutton_write);
        mImageButton.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View view){
                Intent join_intent = new Intent(getContext(), WriteActivity.class);
                startActivity(join_intent);
            }
        });
        dataSetting();
        return view;
    }

    private void dataSetting() {

        MyAdapter mMyAdapter = new MyAdapter();


        for (int i = 0; i < 11; i++) {
            mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.icon), "title_" + i, "date_" + i, "color_" + i, "price_" + i);
        }

        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }


}