package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;

public class DocumentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_user,title,color,number,price,content,date;

    ImageButton imageButtonLeft,imageButtonRight,imageButtonRightSuv;

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
        String st_title = doc_intent.getStringExtra("title");
        String st_color = doc_intent.getStringExtra("color");
        String st_number = doc_intent.getStringExtra("number");
        String st_price = doc_intent.getStringExtra("price");
        String st_date = doc_intent.getStringExtra("date");
        String st_content = doc_intent.getStringExtra("content");
        String st_user = doc_intent.getStringExtra("user"); // 글쓴이
        String login_id = doc_intent.getStringExtra("login"); //로그인한 사람 또는 게스트

        title.setText(st_title);
        color.setText(st_color);
        number.setText(st_number);
        price.setText(st_price);
        content.setText(st_content);
        date.setText(st_date);
        toolbar_user.setText(st_user);

        Toast.makeText(getApplicationContext(),login_id,Toast.LENGTH_SHORT).show();

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
            imageButtonRightSuv.setBackgroundResource(R.drawable.edit);
            imageButtonRightSuv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "admin_suv", Toast.LENGTH_SHORT).show();
                }
            });

            imageButtonRight.setBackgroundResource(R.drawable.delete);
            imageButtonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "admin_right", Toast.LENGTH_SHORT).show();
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

            imageButtonRightSuv.setBackgroundResource(R.drawable.siren);
            imageButtonRightSuv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "suv", Toast.LENGTH_SHORT).show();
                }
            });

            imageButtonRight.setBackgroundResource(R.drawable.message);
            imageButtonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "right", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
