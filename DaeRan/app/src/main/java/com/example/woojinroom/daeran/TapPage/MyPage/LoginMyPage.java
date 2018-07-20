package com.example.woojinroom.daeran.TapPage.MyPage;

import android.content.Intent;
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

    public Button button_logout;
    public TextView text_id;
    public static LoginMyPage newInstance() {
        return new LoginMyPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_loginmypage, container, false);

        text_id = (TextView)view.findViewById(R.id.text_id);

        button_logout=(Button)view.findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent logout_intent = new Intent(getContext(), MainActivity.class);
                logout_intent.putExtra("value",0);
                startActivity(logout_intent);
            }
        });

        return view;// 여기서 UI를 생성해서 View를 return
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
