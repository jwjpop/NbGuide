package com.example.woojinroom.daeran.TapPage.PlayListPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.woojinroom.daeran.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by woojinroom on 2018-01-31.
 */

public class PlayList extends Fragment {

    private ListView mListView;

    private ArrayList<PlayListClass> mInfoArr;
    private PlayListCustomAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public static PlayList newInstance() {
        return new PlayList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_playlist, container, false);

        mListView = (ListView) view.findViewById(R.id.listview_playlist);
        mInfoArr = new ArrayList<PlayListClass>();

        mAdapter = new PlayListCustomAdapter(getContext(), mInfoArr);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //클릭시

            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("playlist"); // 변경값을 확인할 child 이름

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mInfoArr.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    PlayListClass playListClass = messageData.getValue(PlayListClass.class);
                    mInfoArr.add(playListClass);
                    // child 내에 있는 데이터만큼 반복합니다.
                }
                Collections.reverse(mInfoArr);
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mAdapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
