package com.example.woojinroom.nbguide.TapPage.MyPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.woojinroom.nbguide.R;
import com.example.woojinroom.nbguide.TapPage.PlayListPage.PlayListClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by woojin on 2018-09-12.
 */

public class AdminActivitiy extends AppCompatActivity {

    EditText edit_music,edit_musician;
    Button button_admin;
    PlayListClass playListClass;
    String music,musician;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        edit_music = (EditText)findViewById(R.id.edit_music);
        edit_musician = (EditText)findViewById(R.id.edit_musician);

        button_admin = (Button)findViewById(R.id.button_admin);
        button_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music = edit_music.getText().toString();
                musician = edit_musician.getText().toString();
                playListClass = new PlayListClass(music,musician);
                databaseReference.child("playlist").push().setValue(playListClass);
                Toast.makeText(getApplicationContext(),"등록 완료",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onBackPressed() {
        finish();
    }
}
