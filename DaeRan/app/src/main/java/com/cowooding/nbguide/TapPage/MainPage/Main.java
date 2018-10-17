package com.cowooding.nbguide.TapPage.MainPage;

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

import com.cowooding.nbguide.DB.CustomAdapter;
import com.cowooding.nbguide.DB.InfoClass;
import com.cowooding.nbguide.R;
import com.cowooding.nbguide.TapPage.MainPage.Board.BoardClass;
import com.cowooding.nbguide.TapPage.MainPage.WritePage.WriteActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


/**
 * Created by woojinroom on 2018-01-31.
 */

public class Main extends Fragment {

    ImageButton mImageButton;

    String new_id = "guest";
    private ListView mListView;

    private ArrayList<InfoClass> mInfoArr;
    private CustomAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowtime;

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
        if (extra != null) {
            String login_id = extra.getString("id");
            String split_id[] = login_id.split("\\.");
            new_id = split_id[0]+"_"+split_id[1];
        }
        nowtime = getTime();

        //ArrayList 초기화
        mInfoArr = new ArrayList<InfoClass>();

        // doWhileCursorToArray();
        initDatabase();


        //리스트뷰에 사용할 어댑터 초기화(파라메터 Context, ArrayList<InfoClass>)
        mAdapter = new CustomAdapter(getContext(), mInfoArr);
        mListView.setAdapter(mAdapter);

        mReference = mDatabase.getReference("board"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mInfoArr.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    BoardClass boardClass = messageData.getValue(BoardClass.class);
                    InfoClass infoClass = new InfoClass(boardClass);

                    //당일 9시부터 다음날 6시 59분까지의 글 보여줌
                    if ((Integer.parseInt(infoClass.getDate().substring(11, 13)) >= 21) || (Integer.parseInt(infoClass.getDate().substring(11, 13)) <= 6)) {
                        mInfoArr.add(infoClass);
                    }
                }
                Collections.reverse(mInfoArr);
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
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

                Intent doc_intent = new Intent(getContext(), DocumentActivity.class);
                doc_intent.putExtra("title", st_title);
                doc_intent.putExtra("color", st_color);
                doc_intent.putExtra("number", st_number);
                doc_intent.putExtra("price", st_price);
                doc_intent.putExtra("date", st_date);
                doc_intent.putExtra("content", st_content);
                doc_intent.putExtra("user", st_user);
                doc_intent.putExtra("login", new_id);

                startActivity(doc_intent);
                getActivity().finish();
            }
        });

        mImageButton = (ImageButton) view.findViewById(R.id.iamgebutton_write);
        mImageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                
                if (new_id.equals("guest")) {
                    Toast.makeText(getContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    //시간으로 자름(당일 9시부터 다음날 06시 59분까지 작성 가능)
                    if (Integer.parseInt(nowtime.substring(11, 13)) >= 21 || Integer.parseInt(nowtime.substring(11, 13)) <= 6) {

                        Intent write_intent = new Intent(getContext(), WriteActivity.class);
                        write_intent.putExtra("id", new_id);
                        startActivity(write_intent);
                        getActivity().finish();

                    } else {
                        Toast.makeText(getContext(), "당일 21시부터 다음날 06시 59분까지 \n 작성 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
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
        super.onDestroy();

    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("board");
    }

    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}