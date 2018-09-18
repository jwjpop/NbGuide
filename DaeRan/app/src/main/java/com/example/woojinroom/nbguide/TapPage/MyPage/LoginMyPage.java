package com.example.woojinroom.nbguide.TapPage.MyPage;

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

import com.example.woojinroom.nbguide.MainActivity;
import com.example.woojinroom.nbguide.R;
import com.example.woojinroom.nbguide.TapPage.Chat.ChatListActivity;

/**
 * Created by woojinroom on 201801-31.
 */

public class LoginMyPage extends Fragment {

    public Button button_logout,button_chatlist,button_editaccount,button_playlist;
    public TextView text_id;

    String id;

    public static LoginMyPage newInstance() {
        return new LoginMyPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_loginmypage, container, false);

        Bundle extra = getArguments();
        id = extra.getString("id"); //로그인한 아이디

        text_id = (TextView)view.findViewById(R.id.text_id);
        text_id.setText(id);

        button_playlist = (Button)view.findViewById(R.id.button_playlist);

        button_chatlist=(Button)view.findViewById(R.id.button_chatlist);
        button_chatlist.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent_chatlist = new Intent(getContext(),ChatListActivity.class);
                intent_chatlist.putExtra("id",id);
                startActivity(intent_chatlist);
            }
        });

        button_editaccount=(Button)view.findViewById(R.id.button_edit_account);
        button_editaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_edit = new Intent(getContext(),EditAccountActivity.class);
                intent_edit.putExtra("id",id);
                startActivity(intent_edit);
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

        button_playlist.setVisibility(View.INVISIBLE);

        if(id.equals("cowooding"))
        {
            button_playlist.setVisibility(View.VISIBLE);
            button_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent admin_intent = new Intent(getContext(), AdminActivitiy.class);
                    startActivity(admin_intent);
                }
            });
        }

        return view;// 여기서 UI를 생성해서 View를 return
    }


}
