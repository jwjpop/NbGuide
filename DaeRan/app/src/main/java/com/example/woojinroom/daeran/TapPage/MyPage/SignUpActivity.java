package com.example.woojinroom.daeran.TapPage.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by woojin on 2018-07-14.
 */

public class SignUpActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    Toolbar toolbar;
    ImageButton imageButtonLeft,imageButtonRight;
    EditText editText_id,editText_pw,editText_pwchk;

    UserClass userClass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        editText_id=(EditText)findViewById(R.id.edit_id);
        editText_pw=(EditText)findViewById(R.id.edit_pw);
        editText_pwchk=(EditText)findViewById(R.id.edit_pwchk);

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
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass = new UserClass(editText_id.getText().toString(),editText_pw.getText().toString());
                databaseReference.child("user").push().setValue(userClass);
                Toast.makeText(v.getContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });
    }

}