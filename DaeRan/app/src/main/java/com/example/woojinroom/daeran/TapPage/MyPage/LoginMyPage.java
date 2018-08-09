package com.example.woojinroom.daeran.TapPage.MyPage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 201801-31.
 */

public class LoginMyPage extends Fragment {

    public Button button_logout,button_chatlist;
    public TextView text_id;
    public static LoginMyPage newInstance() {
        return new LoginMyPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_loginmypage, container, false);

        Bundle extra = getArguments();
        String id = extra.getString("id");

        text_id = (TextView)view.findViewById(R.id.text_id);
        text_id.setVisibility(View.VISIBLE);
        text_id.setText(id);

        button_chatlist=(Button)view.findViewById(R.id.button_chatlist);
        button_chatlist.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        button_logout=(Button)view.findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                SharedPreferences auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();

                Intent logout_intent = new Intent(getContext(), MainActivity.class);
                startActivity(logout_intent);
                getActivity().finish();
            }
        });

        return view;// 여기서 UI를 생성해서 View를 return
    }


}
