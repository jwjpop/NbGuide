package com.example.woojinroom.daeran.TapPage.InfoPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 2018-01-31.
 */

public class Info extends Fragment {

    Button map,menu1,menu2;
    public static Info newInstance() {
        return new Info();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view =  inflater.inflate(R.layout.fragment_info, container, false);

        map =(Button)view.findViewById(R.id.button_map);
        menu1 =(Button)view.findViewById(R.id.button_menu1);
        menu2 =(Button)view.findViewById(R.id.button_menu2);
        return view;
    }
}
