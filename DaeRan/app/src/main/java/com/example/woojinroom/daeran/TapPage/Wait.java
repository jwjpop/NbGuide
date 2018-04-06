package com.example.woojinroom.daeran.TapPage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 2018-01-31.
 */

public class Wait extends Fragment {

    public static Wait newInstance() {
        return new Wait();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wait, container, false); // 여기서 UI를 생성해서 View를 return
    }
}
