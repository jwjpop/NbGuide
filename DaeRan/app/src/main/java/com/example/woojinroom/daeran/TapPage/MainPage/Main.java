package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.woojinroom.daeran.DB.CustomAdapter;
import com.example.woojinroom.daeran.DB.InfoClass;
import com.example.woojinroom.daeran.R;
import com.example.woojinroom.daeran.TapPage.MainPage.BoardClass.BoardClass;
import com.example.woojinroom.daeran.TapPage.MainPage.WritePage.WriteActivity;
import com.google.firebase.database.ChildEventListener;
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

public class Main extends Fragment {
    ImageButton mImageButton;

    private ListView mListView;

    private InfoClass mInfoClass;
    private ArrayList<InfoClass> mInfoArr;
    private CustomAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    public static Main newInstance() {
        return new Main();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view,custom_view;
        view = inflater.inflate(R.layout.fragment_main, container, false); // 여기서 UI를 생성해서 View를 return
        custom_view = inflater.inflate(R.layout.listview_custom_main, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);

        mImageButton = (ImageButton)view.findViewById(R.id.iamgebutton_write);
        mImageButton.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(getContext(),"글작성버튼 준비중입니다.",Toast.LENGTH_SHORT).show();
              /*  Intent write_intent = new Intent(getContext(), WriteActivity.class);
                startActivity(write_intent);*/
            }
        });

        //ArrayList 초기화
        mInfoArr = new ArrayList<InfoClass>();

       // doWhileCursorToArray();
        initDatabase();

        //리스트뷰에 사용할 어댑터 초기화(파라메터 Context, ArrayList<InfoClass>)
        mAdapter = new CustomAdapter(getContext(), mInfoArr);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
        mReference = mDatabase.getReference("board"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            mInfoArr.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    BoardClass boardClass = messageData.getValue(BoardClass.class);
                    InfoClass infoClass = new InfoClass(boardClass);
                    mInfoArr.add(infoClass);
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
        //리스트뷰의 아이템을 길게 눌렀을 경우 삭제하기 위해 롱클릭 리스너 따로 설정

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    //액티비티가 종료 될 때 디비를 닫아준다
    @Override
    public void onDestroy() {
        mReference.removeEventListener(mChild);
        super.onDestroy();

    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("board");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }
}