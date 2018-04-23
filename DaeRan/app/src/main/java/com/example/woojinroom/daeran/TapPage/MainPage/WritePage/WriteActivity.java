package com.example.woojinroom.daeran.TapPage.MainPage.WritePage;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woojinroom.daeran.DbOpenHelper;
import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 2018-04-23.
 */

public class WriteActivity extends AppCompatActivity {

    private DbOpenHelper mDbOpenHelper;

    Toolbar toolbar;
    TextView toolbar_title;
    ImageButton imageButtonLeft;
    ImageButton imageButtonRight;

    Spinner color;
    EditText title,price,content;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mDbOpenHelper = new DbOpenHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("글 작성");

        title = (EditText)findViewById(R.id.edit_title);
        color = (Spinner) findViewById(R.id.spinner_color);
        price = (EditText)findViewById(R.id.edit_price);
        content = (EditText)findViewById(R.id.edit_content);

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
                mDbOpenHelper.insertColumn("송중기", "01011223344", "angel@google.com");
                finish();
            }
        });
    }
}
