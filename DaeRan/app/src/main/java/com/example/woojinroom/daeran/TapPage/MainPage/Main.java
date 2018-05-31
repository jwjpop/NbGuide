package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.example.woojinroom.daeran.DB.DataBases;
import com.example.woojinroom.daeran.DB.DbOpenHelper;
import com.example.woojinroom.daeran.DB.InfoClass;
import com.example.woojinroom.daeran.R;
import com.example.woojinroom.daeran.TapPage.MainPage.WritePage.WriteActivity;

import java.util.ArrayList;


/**
 * Created by woojinroom on 2018-01-31.
 */

public class Main extends Fragment {

    ImageButton mImageButton;

    private ListView mListView;
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    private SQLiteDatabase db;
    private InfoClass mInfoClass;
    private ArrayList<InfoClass> mInfoArr;
    private CustomAdapter mAdapter;



    public static Main newInstance() {
        return new Main();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_main, container, false); // 여기서 UI를 생성해서 View를 return
        mListView = (ListView) view.findViewById(R.id.listView);
        mImageButton = (ImageButton)view.findViewById(R.id.iamgebutton_write);
        mImageButton.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(getContext(),"글작성버튼 준비중입니다.",Toast.LENGTH_SHORT).show();
              /*  Intent write_intent = new Intent(getContext(), WriteActivity.class);
                startActivity(write_intent);*/
            }
        });

        mDbOpenHelper = new DbOpenHelper(getContext());
        mDbOpenHelper.open();

        //ArrayList 초기화
        mInfoArr = new ArrayList<InfoClass>();

        doWhileCursorToArray();

        //리스트뷰에 사용할 어댑터 초기화(파라메터 Context, ArrayList<InfoClass>)
        mAdapter = new CustomAdapter(getContext(), mInfoArr);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
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

    private void doWhileCursorToArray() {

        mCursor = null;
        //DB에 있는 모든 컬럼을 가져옴
        mCursor = mDbOpenHelper.getAllColumns();

        db=mDbOpenHelper.mDBHelper.getWritableDatabase();

        mCursor = db.rawQuery("SELECT * FROM text4 order by date desc", null);

        while (mCursor.moveToNext()) {
            //InfoClass에 입력된 값을 압력
            mInfoClass = new InfoClass(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("title")),
                    mCursor.getString(mCursor.getColumnIndex("date")),
                    mCursor.getString(mCursor.getColumnIndex("color")),
                    mCursor.getString(mCursor.getColumnIndex("number")),
                    mCursor.getString(mCursor.getColumnIndex("price"))
            );
            //입력된 값을 가지고 있는 InfoClass를 InfoArray에 add
            mInfoArr.add(mInfoClass);
        }
        //Cursor 닫기
        mCursor.close();
    }

    //액티비티가 종료 될 때 디비를 닫아준다
    @Override
    public void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }






}