package com.cowooding.nbguide.TapPage.MainPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cowooding.nbguide.MainActivity;
import com.cowooding.nbguide.R;
import com.cowooding.nbguide.TapPage.Chat.ChatActivity;
import com.cowooding.nbguide.TapPage.MainPage.WritePage.EditActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DocumentActivity extends AppCompatActivity{

    Toolbar toolbar;
    TextView toolbar_user,title,color,number,price,content,date;

    ImageButton imageButtonLeft,imageButtonRight,imageButtonRightSuv;

    String st_title,st_color,st_number,st_price,st_date,st_content,st_user,login_id;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        //툴바 설정
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_user = (TextView) toolbar.findViewById(R.id.title);
        toolbar_user.setText("USER");

        title = (TextView)findViewById(R.id.doc_title);
        color = (TextView)findViewById(R.id.doc_color);
        number = (TextView)findViewById(R.id.doc_number);
        price = (TextView)findViewById(R.id.doc_price);
        content = (TextView)findViewById(R.id.doc_content);
        date = (TextView)findViewById(R.id.doc_date);

        Intent doc_intent = getIntent();
        st_title = doc_intent.getStringExtra("title");
        st_color = doc_intent.getStringExtra("color");
        st_number = doc_intent.getStringExtra("number");
        st_price = doc_intent.getStringExtra("price");
        st_date = doc_intent.getStringExtra("date");     // 작성 시간
        st_content = doc_intent.getStringExtra("content");
        st_user = doc_intent.getStringExtra("user"); // 작성자
        login_id = doc_intent.getStringExtra("login"); //로그인한 사람 또는 게스트

        title.setText(st_title);
        color.setText(st_color);
        number.setText(st_number);
        price.setText(st_price);
        content.setText(st_content);
        date.setText(st_date);
        toolbar_user.setText(st_user);

        //왼쪽 버튼
        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });

        imageButtonRightSuv = (ImageButton) toolbar.findViewById(R.id.imagebutton_right_suv);
        imageButtonRightSuv.setVisibility(View.VISIBLE);
        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);

        //작성자,관리자일 경우와 일반인의 경우
        if(login_id.equals(st_user)) {
            //수정
            imageButtonRightSuv.setBackgroundResource(R.drawable.edit);
            imageButtonRightSuv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent edit_intent = new Intent(getApplicationContext(), EditActivity.class);

                    edit_intent.putExtra("title",st_title);
                    edit_intent.putExtra("number",st_number);
                    edit_intent.putExtra("price",st_price);
                    edit_intent.putExtra("content",st_content);
                    edit_intent.putExtra("date",st_date);
                    edit_intent.putExtra("user",st_user);

                    startActivity(edit_intent);

                }
            });

            imageButtonRight.setBackgroundResource(R.drawable.delete);
            imageButtonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(DocumentActivity.this);
                    builder.setTitle("삭제 경고");
                    builder.setMessage("정말로 글을 삭제하시겠습니까?");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mDatabase = FirebaseDatabase.getInstance();
                                    mReference = mDatabase.getReference("board/"+st_date+"_"+st_user);
                                    mReference.removeValue();
                                    //mReference = mDatabase.getReference("user/"+st_user+"/write");
                                    //mReference.setValue("0");

                                    Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(refresh_intent);
                                    finish();
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }
            });
        }
        else if(login_id.equals("guest")){
            imageButtonRightSuv.setBackgroundResource(R.drawable.siren);
            imageButtonRightSuv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            });

            imageButtonRight.setBackgroundResource(R.drawable.message);
            imageButtonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            //신고
            imageButtonRightSuv.setBackgroundResource(R.drawable.siren);
            imageButtonRightSuv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("plain/text");
                    // email setting 배열로 해놔서 복수 발송 가능
                    String[] address = {"jwjpop@gmail.com"};
                    email.putExtra(Intent.EXTRA_EMAIL, address);
                    email.putExtra(Intent.EXTRA_SUBJECT,"제목");
                    email.putExtra(Intent.EXTRA_TEXT,"본문");
                    startActivity(Intent.createChooser(email,"Send mail"));

                }
            });
            //메세지
            imageButtonRight.setBackgroundResource(R.drawable.message);
            imageButtonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chat_intent = new Intent(getApplicationContext(), ChatActivity.class);
                    chat_intent.putExtra("user",st_user);
                    chat_intent.putExtra("sender",login_id);
                    startActivity(chat_intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(refresh_intent);
        finish();
    }
}
