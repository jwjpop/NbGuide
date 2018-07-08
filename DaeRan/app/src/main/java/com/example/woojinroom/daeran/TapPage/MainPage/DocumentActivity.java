package com.example.woojinroom.daeran.TapPage.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;

public class DocumentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_user,title,color,number,price,content,date;

    ImageButton imageButtonLeft;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        //툴바 설정
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_user = (TextView) toolbar.findViewById(R.id.title);
        toolbar_user.setText("USER");

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });

        title = (TextView)findViewById(R.id.doc_title);
        color = (TextView)findViewById(R.id.doc_color);
        number = (TextView)findViewById(R.id.doc_number);
        price = (TextView)findViewById(R.id.doc_price);
        content = (TextView)findViewById(R.id.doc_content);
        date = (TextView)findViewById(R.id.doc_date);

    }
}
