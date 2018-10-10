package com.cowooding.nbguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SplashActivity extends AppCompatActivity {

    ImageView img_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img_splash = (ImageView)findViewById(R.id.img_splash);
        FirebaseStorage fs = FirebaseStorage.getInstance();
        StorageReference imagesRef = fs.getReference().child("image/drawable-xhdpi/image_splash.png");
        Glide.with(this)
                .load(imagesRef)
                .into(img_splash);

        try {
            Thread.sleep(2000);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}