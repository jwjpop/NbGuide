package com.example.woojinroom.daeran.TapPage.InfoPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.woojinroom.daeran.R;
import com.example.woojinroom.daeran.TapPage.MainPage.WritePage.WriteActivity;

/**
 * Created by woojinroom on 2018-01-31.
 */

public class Info extends Fragment {

    Button map1f,map2f,menu1,menu2,enterinfo;
    public static Info newInstance() {
        return new Info();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view =  inflater.inflate(R.layout.fragment_info, container, false);

        map1f =(Button)view.findViewById(R.id.button_map_1f);
        map1f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu1_intent = new Intent(getContext(), ImageActivity.class);
                menu1_intent.putExtra("menu",3);
                startActivity(menu1_intent);
            }
        });

        map2f =(Button)view.findViewById(R.id.button_map_2f);
        map2f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu1_intent = new Intent(getContext(), ImageActivity.class);
                menu1_intent.putExtra("menu",4);
                startActivity(menu1_intent);
            }
        });

        menu1 =(Button)view.findViewById(R.id.button_menu1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu1_intent = new Intent(getContext(), ImageActivity.class);
                menu1_intent.putExtra("menu",1);
                startActivity(menu1_intent);
            }
        });

        menu2 =(Button)view.findViewById(R.id.button_menu2);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu2_intent = new Intent(getContext(), ImageActivity.class);
                menu2_intent.putExtra("menu",2);
                startActivity(menu2_intent);
            }
        });

        enterinfo =(Button)view.findViewById(R.id.button_enterinfo);
        enterinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"입장정보 준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
