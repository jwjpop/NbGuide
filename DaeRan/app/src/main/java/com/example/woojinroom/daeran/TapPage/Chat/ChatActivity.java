package com.example.woojinroom.daeran.TapPage.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;

/**
 * Created by woojin on 2018-08-09.
 */

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_user;

    ImageButton imageButtonLeft,imageButtonRight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setVisibility(View.INVISIBLE);
    }
}
