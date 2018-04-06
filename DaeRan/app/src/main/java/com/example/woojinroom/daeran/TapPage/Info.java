package com.example.woojinroom.daeran.TapPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 2018-01-31.
 */

public class Info extends Fragment {

    public static Info newInstance() {
        return new Info();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false); // 여기서 UI를 생성해서 View를 return
    }
}
