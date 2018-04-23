package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.woojinroom.daeran.DB.Constants;
import com.example.woojinroom.daeran.DB.CustomAdapter;
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

    private EditText[] mEditTexts;
    private ListView mListView;
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
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
                Intent join_intent = new Intent(getContext(), WriteActivity.class);
                startActivity(join_intent);
            }
        });
        //dataSetting(); DB쓰기전에 쓰던 메소드

        mDbOpenHelper = new DbOpenHelper(getContext());
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //ArrayList 초기화
        mInfoArr = new ArrayList<InfoClass>();

        doWhileCursorToArray();

        //리스트뷰에 사용할 어댑터 초기화(파라메터 Context, ArrayList<InfoClass>)
        mAdapter = new CustomAdapter(getContext(), mInfoArr);
        mListView.setAdapter(mAdapter);
        //리스트뷰의 아이템을 길게 눌렀을 경우 삭제하기 위해 롱클릭 리스너 따로 설정
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                //리스트뷰의 position은 0부터 시작하므로 1을 더함
                boolean result = mDbOpenHelper.deleteColumn(position + 1);

                if (result) {
                    //정상적인 position을 가져왔을 경우 ArrayList의 position과 일치하는 index 정보를 remove
                    mInfoArr.remove(position);
                    //어댑터에 ArrayList를 다시 세팅 후 값이 변경됬다고 어댑터에 알림
                    mAdapter.setArrayList(mInfoArr);
                    mAdapter.notifyDataSetChanged();
                } else {
                    //잘못된 position을 가져왔을 경우 다시 확인 요청
                    Toast.makeText(getContext(), "INDEX를 확인해 주세요",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        return view;
    }
    private void doWhileCursorToArray() {

        mCursor = null;
        //DB에 있는 모든 컬럼을 가져옴
        mCursor = mDbOpenHelper.getAllColumns();
        //컬럼의 갯수 확인

        while (mCursor.moveToNext()) {
            //InfoClass에 입력된 값을 압력
            mInfoClass = new InfoClass(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("name")),
                    mCursor.getString(mCursor.getColumnIndex("contact")),
                    mCursor.getString(mCursor.getColumnIndex("email"))
            );
            //입력된 값을 가지고 있는 InfoClass를 InfoArray에 add
            mInfoArr.add(mInfoClass);
        }
        //Cursor 닫기
        mCursor.close();
    }

    public void btnAdd(View v) {
        //추가를 누를 경우 EditText에 있는 String 값을 다 가져옴
        mDbOpenHelper.insertColumn(
                mEditTexts[Constants.NAME].getText().toString().trim(),
                mEditTexts[Constants.CONTACT].getText().toString().trim(),
                mEditTexts[Constants.EMAIL].getText().toString().trim()
        );
        //ArrayList 내용 삭제
        mInfoArr.clear();

        doWhileCursorToArray();

        mAdapter.setArrayList(mInfoArr);
        mAdapter.notifyDataSetChanged();
        //Cursor 닫기
        mCursor.close();
    }

    //액티비티가 종료 될 때 디비를 닫아준다
    @Override
    public void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }



   /* DB쓰기전에 쓰던 메소드
    private void dataSetting() {

        MyAdapter mMyAdapter = new MyAdapter();


        for (int i = 0; i < 11; i++) {
            mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.icon), "title_" + i, "date_" + i, "color_" + i, "price_" + i);
        }

        mListView.setAdapter(mMyAdapter);
    }*/


}