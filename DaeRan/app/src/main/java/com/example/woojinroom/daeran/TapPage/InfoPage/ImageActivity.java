package com.example.woojinroom.daeran.TapPage.InfoPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.woojinroom.daeran.R;

/**
 * Created by woojinroom on 2018-05-10.
 */

public class ImageActivity extends AppCompatActivity {
    Button back;
    ImageView imageView;
    int menu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        back=(Button)findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView =(ImageView)findViewById(R.id.image_info);
        Intent get_intent;
        get_intent = getIntent();
        menu = get_intent.getIntExtra("menu",1);
        if(menu==1){
            imageView.setImageResource(R.drawable.menu1_180503);
        }else if(menu==2)
        {
            imageView.setImageResource(R.drawable.menu2_180503);
        }

    }

}
