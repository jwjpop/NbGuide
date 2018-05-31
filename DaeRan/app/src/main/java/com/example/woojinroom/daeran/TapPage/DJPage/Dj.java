package com.example.woojinroom.daeran.TapPage.DJPage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.woojinroom.daeran.R;

import java.util.ArrayList;

/**
 * Created by woojinroom on 2018-01-31.
 */

public class Dj extends Fragment {

    ListView listView;

    public static Dj newInstance() {
        return new Dj();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_dj, container, false);

        listView = (ListView)view.findViewById(R.id.listview_dj);
        ArrayList<Listviewitem> data=new ArrayList<>();
        Listviewitem dj1=new Listviewitem(R.drawable.book);
        Listviewitem dj2=new Listviewitem(R.drawable.cake);
        Listviewitem dj3=new Listviewitem(R.drawable.dj);
        Listviewitem dj4=new Listviewitem(R.drawable.id);

        data.add(dj1);
        data.add(dj2);
        data.add(dj3);
        data.add(dj4);

        ListviewAdapter adapter=new ListviewAdapter(view.getContext(),R.layout.dj_item,data);
        listView.setAdapter(adapter);
        return view;
    }
}
