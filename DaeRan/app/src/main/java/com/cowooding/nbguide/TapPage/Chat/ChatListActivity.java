package com.cowooding.nbguide.TapPage.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cowooding.nbguide.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by woojin on 2018-08-11.
 */

public class ChatListActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_title;

    ImageButton imageButtonLeft,imageButtonRight;

    String login_id;

    private ListView mListView;
    private ArrayList<String> mChatList;
    private ChatListCustomAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String day;
    int time;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("채팅 목록");

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setVisibility(View.INVISIBLE);

        Intent id_intent = getIntent();
        login_id = id_intent.getStringExtra("id");

        mListView = (ListView) findViewById(R.id.listView_chatlist);
        mChatList = new ArrayList<String>();

        mAdapter = new ChatListCustomAdapter(getApplicationContext(), mChatList,login_id);
        mListView.setAdapter(mAdapter);

        //오늘 날짜
        day = getTime().substring(8,10);
        time = Integer.parseInt(getTime().substring(11,13));

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("chat"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mChatList.clear();

                        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                            String id[] = messageData.getKey().split(" ");

                               //현재는 모든 챗리스트 등록
                                    if (id[0].equals(login_id) && !id[1].equals(login_id)) {
                                        mChatList.add(id[1]);
                                    } else if (!id[0].equals(login_id) && id[1].equals(login_id)) {
                                        mChatList.add(id[0]);
                                    }

                            mAdapter.notifyDataSetChanged();
                            mListView.setSelection(0);

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Intent chat_intent = new Intent(getApplicationContext(), ChatActivity.class);
                chat_intent.putExtra("user",mChatList.get(position));
                chat_intent.putExtra("sender",login_id);
                startActivity(chat_intent);
            }
        });
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public void onBackPressed() {
        finish();
    }
}
