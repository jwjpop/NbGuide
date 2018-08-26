package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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

    String login_id="guest";

    private ListView mListView;

    private ArrayList<InfoClass> mInfoArr;
    private CustomAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    Bundle extra;
    public static Main newInstance() {
        return new Main();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_main, container, false); // 여기서 UI를 생성해서 View를 return
        mListView = (ListView) view.findViewById(R.id.listView);

        extra = getArguments();

        mImageButton = (ImageButton)view.findViewById(R.id.iamgebutton_write);
        mImageButton.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View view){

                if(extra!=null) { //널이 아니면
                    login_id = extra.getString("id");
                }

                if(login_id.equals("guest")){
                    Toast.makeText(getContext(),"로그인이 필요합니다.",Toast.LENGTH_SHORT).show();
                } else {
                    Intent write_intent = new Intent(getContext(), WriteActivity.class);
                    write_intent.putExtra("id", login_id);
                    startActivity(write_intent);
                    getActivity().finish();
                }
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

                TextView tx_title = (TextView) arg0.getChildAt(position).findViewById(R.id.tv_title);
                String st_title = tx_title.getText().toString();

                TextView tx_color = (TextView) arg0.getChildAt(position).findViewById(R.id.tv_color);
                String st_color = tx_color.getText().toString();

                TextView tx_number = (TextView) arg0.getChildAt(position).findViewById(R.id.tv_number);
                String st_number = tx_number.getText().toString();

                TextView tx_price = (TextView) arg0.getChildAt(position).findViewById(R.id.tv_price);
                String st_price = tx_price.getText().toString();

                TextView tx_date = (TextView) arg0.getChildAt(position).findViewById(R.id.tv_date);
                String st_date = tx_date.getText().toString();

                TextView tx_content = (TextView) arg0.getChildAt(position).findViewById(R.id.tv_content);
                String st_content = tx_content.getText().toString();

                TextView tx_user = (TextView) arg0.getChildAt(position).findViewById(R.id.tv_user);
                String st_user = tx_user.getText().toString();

                if(extra!=null) {
                    login_id = extra.getString("id");
                }

                Intent doc_intent = new Intent(getContext(), DocumentActivity.class);
                doc_intent.putExtra("title",st_title);
                doc_intent.putExtra("color",st_color);
                doc_intent.putExtra("number",st_number);
                doc_intent.putExtra("price",st_price);
                doc_intent.putExtra("date",st_date);
                doc_intent.putExtra("content",st_content);
                doc_intent.putExtra("user",st_user);
                doc_intent.putExtra("login",login_id);

                startActivity(doc_intent);
                getActivity().finish();
            }
        });

        //여기 코드엔 initdatabase 안에 이미 들어있음 복사용
       // mDatabase = FirebaseDatabase.getInstance();
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

    /*@Override
    public void onBack() {

            MainActivity activity = (MainActivity) getActivity();
            activity.setOnKeyBackPressedListener(null);
            activity.onBackPressed();

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).setOnKeyBackPressedListener(this);
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mainFragment).commit();
    }*/
    //나중에 뒤로가기 수정
}