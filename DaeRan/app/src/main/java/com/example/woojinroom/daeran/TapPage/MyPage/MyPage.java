package com.example.woojinroom.daeran.TapPage.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 201801-31.
 */

public class MyPage extends Fragment {

    public Button button_login,button_signUp;
    public static MyPage newInstance() {
        return new MyPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        button_login=(Button)view.findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent_login = new Intent(getContext(),LoginActivity.class);
                startActivity(intent_login);
            }
        });

        button_signUp=(Button)view.findViewById(R.id.button_signUp);
        button_signUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent_signUp = new Intent(getContext(),SignUpActivity.class);
                startActivity(intent_signUp);
            }
        });



        return view;// 여기서 UI를 생성해서 View를 return
    }
}
