package com.example.woojinroom.daeran.TapPage.InfoPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.woojinroom.daeran.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by woojinroom on 2018-05-10.
 */

public class ImageActivity extends AppCompatActivity {
    Button back;
    PhotoView mimageView;
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

        mimageView =(PhotoView) findViewById(R.id.image_info);

        Intent get_intent;
        get_intent = getIntent();
        menu = get_intent.getIntExtra("menu",1);

        if(menu==1){
            mimageView.setImageResource(R.drawable.menu1_180503);
        }else if(menu==2)
        {
            mimageView.setImageResource(R.drawable.menu2_180503);
        }
        else if(menu==3)
        {
            mimageView.setImageResource(R.drawable.map_1f);
        }
        else  if (menu==4)
        {
            mimageView.setImageResource(R.drawable.map_2f);
        }

    }

}
