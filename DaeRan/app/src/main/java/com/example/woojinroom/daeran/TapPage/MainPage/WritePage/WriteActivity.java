package com.example.woojinroom.daeran.TapPage.MainPage.WritePage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 2018-04-23.
 */

public class WriteActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbar_title;
    ImageButton imageButtonLeft;
    ImageButton imageButtonRight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("글 작성");
        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"미 구현",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
