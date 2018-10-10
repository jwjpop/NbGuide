package com.cowooding.nbguide.TapPage.MyPage;

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
import android.widget.Toast;

import com.cowooding.nbguide.MainActivity;
import com.cowooding.nbguide.R;
import com.cowooding.nbguide.TapPage.Chat.ChatListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by woojinroom on 201801-31.
 */

public class LoginMyPage extends Fragment {

    public Button button_logout,button_chatlist,button_playlist,button_setup;
    public TextView text_id;

    String id;
    String new_id;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int time;

    public static LoginMyPage newInstance() {
        return new LoginMyPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_loginmypage, container, false);

        Bundle extra = getArguments();
        id = extra.getString("id"); //로그인한 아이디
        String split_id[] = id.split("\\.");
        new_id = split_id[0]+"_"+split_id[1];

        text_id = (TextView)view.findViewById(R.id.text_id);
        text_id.setText(new_id);

        time =Integer.parseInt(getTime().substring(11,13));
        button_playlist = (Button)view.findViewById(R.id.button_playlist);

        button_chatlist=(Button)view.findViewById(R.id.button_chatlist);
        button_chatlist.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(time >= 21 || time <= 6) {
                    Intent intent_chatlist = new Intent(getContext(), ChatListActivity.class);
                    intent_chatlist.putExtra("id", new_id);
                    startActivity(intent_chatlist);
                }
                else
                {
                    Toast.makeText(getContext(),"당일 21시부터 다음날 6시59분까지 \n 이용하실 수 있습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_setup=(Button)view.findViewById(R.id.button_setup);
        button_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_setup = new Intent(getContext(),SetupActivity.class);
                intent_setup.putExtra("new_id",new_id);
                startActivity(intent_setup);
                getActivity().finish();
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
        //관리자일 때
        if(id.equals("cowooding@naver.com"))
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
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

}
