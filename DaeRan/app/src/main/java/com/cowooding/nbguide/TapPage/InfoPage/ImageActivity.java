package com.cowooding.nbguide.TapPage.InfoPage;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.cowooding.nbguide.DB.GlideApp;
import com.cowooding.nbguide.DB.MyAppGlideModule;
import com.cowooding.nbguide.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by woojinroom on 2018-05-10.
 */

public class ImageActivity extends AppCompatActivity {
    Button back;
    PhotoView mimageView;
    int menu;
    String info;
    TextView tv_info;
    int width;
    String str_width;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A6F26748DB789BFFD7C97C18BD4A7B5").build();
        mAdView.loadAd(adRequest);

        back=(Button)findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //해상도 구하기
        /*Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;

        if(width>=1440){
            str_width = "xxx";
        }
        else if(width>=960)
        {
            str_width="xx";
        }
        else if(width>=720)
        {
            str_width="x";
        }
        else if(width>=480)
        {
            str_width="";
        }
        else
        {
            str_width="m";
        }
        FirebaseStorage fs = FirebaseStorage.getInstance();*/

        mimageView =(PhotoView) findViewById(R.id.image_info);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setMovementMethod(new ScrollingMovementMethod());
        final Intent get_intent;
        get_intent = getIntent();
        menu = get_intent.getIntExtra("menu",1);

        if(menu==1)
        {
            tv_info.setVisibility(View.INVISIBLE);
            mimageView.setVisibility(View.VISIBLE);
           /* StorageReference imagesRef = fs.getReference("image/drawable-"+str_width+"hdpi/menu1_180503.png");
            GlideApp.with(this)
                    .load(imagesRef)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mimageView);*/
            mimageView.setImageResource(R.drawable.menu2_180503);
        }else if(menu==2)
        {
            tv_info.setVisibility(View.INVISIBLE);
            mimageView.setVisibility(View.VISIBLE);
            mimageView.setImageResource(R.drawable.menu2_180503);
        }
        else if(menu==3)
        {
            tv_info.setVisibility(View.INVISIBLE);
            mimageView.setVisibility(View.VISIBLE);
            mimageView.setImageResource(R.drawable.map_1f);
        }
        else  if (menu==4)
        {
            tv_info.setVisibility(View.INVISIBLE);
            mimageView.setVisibility(View.VISIBLE);
            mimageView.setImageResource(R.drawable.map_2f);
        }
        else  if (menu==5)
        {
            mimageView.setVisibility(View.INVISIBLE);
            tv_info.setVisibility(View.VISIBLE);
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getReference("enterinfo"); // 변경값을 확인할 child 이름

            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                        info = messageData.getValue().toString();
                    }

                    tv_info.setText(info);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
