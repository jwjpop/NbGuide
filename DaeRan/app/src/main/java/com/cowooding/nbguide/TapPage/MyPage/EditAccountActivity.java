package com.cowooding.nbguide.TapPage.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cowooding.nbguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woojin on 2018-08-13.
 */

public class EditAccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView text_toolbar;

    ImageButton imageButtonLeft,imageButtonRight;
    EditText editText_pwnow,editText_pwnew,getEditText_pwnewagain;

    String pwnow,pwnew,pwnewagain,id;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);

        Intent id_intent = getIntent();
        id = id_intent.getStringExtra("id");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        text_toolbar = (TextView)toolbar.findViewById(R.id.title);
        text_toolbar.setText("비밀번호 수정");

        editText_pwnow = (EditText)findViewById(R.id.edit_pwnow);
        editText_pwnew = (EditText)findViewById(R.id.edit_pwnew);
        getEditText_pwnewagain = (EditText)findViewById(R.id.edit_pwnewagain);



        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("user/");

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pwnow = editText_pwnow.getText().toString();
                pwnew = editText_pwnew.getText().toString();
                pwnewagain = getEditText_pwnewagain.getText().toString();

                if (!pwnow.equals("")&&!pwnew.equals("")&&!pwnewagain.equals("")) {

                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                UserClass db_user = messageData.getValue(UserClass.class);
                                if(db_user.getId().equals(id)){ //아이디가 일치한 경우
                                    if(db_user.getPw().equals(pwnow)){ //현재 비밀번호가 일치한 경우

                                        if(pwnew.equals(pwnewagain)){ //새 비밀번호끼리 일치한 경우
                                            if(validatePassword(pwnew)) {
                                                UserClass userClass = new UserClass(id, pwnew, db_user.getWrite(),db_user.getToken());
                                                databaseReference.child("user").child(id).setValue(userClass);
                                                Toast.makeText(getApplicationContext(), "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();

                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(),"4~16자리를 입력해주세요",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"새 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"현재 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                // child 내에 있는 데이터만큼 반복합니다.
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed() {
        finish();
    }
    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr); return matcher.matches();
    }
}
