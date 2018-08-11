package com.example.woojinroom.daeran.TapPage.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by woojin on 2018-08-09.
 */

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_user;

    ImageButton imageButtonLeft,imageButtonRight;

    Button button_send;
    EditText editText_text;

    String user,date,text,sender;

    private ListView mListView;
    private ArrayList<ChatClass> mChatArr;
    private ChatCustomAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    ChatClass chat_send;

    int room = 0;
    Intent chat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat = getIntent();
        user = chat.getStringExtra("user");
        sender = chat.getStringExtra("sender");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_user = (TextView) toolbar.findViewById(R.id.title);
        toolbar_user.setText(user);

        mListView = (ListView) findViewById(R.id.listView_chat);
        mChatArr = new ArrayList<ChatClass>();

        mAdapter = new ChatCustomAdapter(getApplicationContext(), mChatArr,sender);
        mListView.setAdapter(mAdapter);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("chat/" + sender + " " + user); // 변경값을 확인할 child 이름


            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(getApplicationContext(),"변경",Toast.LENGTH_SHORT).show();
                    if(dataSnapshot.getValue()==null) //첫 검사 때 방이 없는 경우
                    {
                        room=1;
                        mReference = mDatabase.getReference("chat/" + user + " " + sender);
                        mReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                mChatArr.clear();
                                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                    ChatClass chatClass = messageData.getValue(ChatClass.class);
                                    mChatArr.add(chatClass);
                                    // child 내에 있는 데이터만큼 반복합니다.
                                }
                                mAdapter.notifyDataSetChanged();
                                mListView.setSelection(mAdapter.getCount() - 1);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else { //방이 아예 없거나 메세지 받은 적이 있는 모든 경우

                        mChatArr.clear();
                        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                            ChatClass chatClass = messageData.getValue(ChatClass.class);
                            mChatArr.add(chatClass);
                            // child 내에 있는 데이터만큼 반복합니다.

                            mAdapter.notifyDataSetChanged();
                            mListView.setSelection(mAdapter.getCount() - 1);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            editText_text = (EditText) findViewById(R.id.edit_send);

            button_send = (Button) findViewById(R.id.button_send);
            button_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    date = getTime();
                    text = editText_text.getText().toString();
                    chat_send = new ChatClass(sender, date, text);

                    if (room == 0) { //원래 방이 있으면
                        databaseReference.child("chat").child(sender + " " + user).push().setValue(chat_send);
                    }
                    else //방이 없거나 받기만 했으면
                    {
                        databaseReference.child("chat").child(user + " " + sender).push().setValue(chat_send);
                    }

                    editText_text.setText("");
                }
            });

            imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
            imageButtonLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(refresh_intent);
                    finish();
                }
            });

            imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
            imageButtonRight.setVisibility(View.INVISIBLE);

        }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    public void onBackPressed() {
        Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(refresh_intent);
        finish();
    }
}
