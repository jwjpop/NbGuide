package com.example.woojinroom.daeran.TapPage.MyPage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woojin on 2018-07-14.
 */

public class SignUpActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    Toolbar toolbar;
    ImageButton imageButtonLeft,imageButtonRight;
    EditText editText_id,editText_pw,editText_pwchk;
    String id,pw,pwchk;
    Button button_equals;

    int id_count=0;
    int id_chk=0;
    int sign=0;

    public static final Pattern VALID_ID_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9]{4,10}$");
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        editText_id=(EditText)findViewById(R.id.edit_id);
        editText_pw=(EditText)findViewById(R.id.edit_pw);
        editText_pwchk=(EditText)findViewById(R.id.edit_pwchk);

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("user"); // 변경값을 확인할 child 이름

        button_equals = (Button)findViewById(R.id.button_equals);
        button_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id_chk = 0;
                id_count = 0;

                id = editText_id.getText().toString();

                if (!id.equals("")) {
                    if(validateId(id)){

                        mReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                    UserClass db_user = messageData.getValue(UserClass.class);
                                    // child 내에 있는 데이터만큼 반복합니다.
                                    if (id.equals(db_user.getId())) {
                                        id_count++;
                                    }
                                }

                                if (id_count != 0) {
                                    if (sign == 0) {
                                        Toast.makeText(getApplicationContext(), "중복 된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    id_chk = 1;
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "4~10자로 영문자 또는 영문자와 숫자를 조합해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = editText_id.getText().toString();
                pw = editText_pw.getText().toString();
                pwchk = editText_pwchk.getText().toString();

                if(id_chk==1) {
                    if(validatePassword(pw)){
                        if (pw.equals(pwchk)) {
                            UserClass userClass = new UserClass(id, pw,"0");
                            databaseReference.child("user").child(id).setValue(userClass);
                            Toast.makeText(v.getContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                            sign = 1;

                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putString("inputId", id);
                            autoLogin.putString("inputPwd", pw);
                            autoLogin.commit();

                            Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(refresh_intent);
                            finish();
                        } else {
                            Toast.makeText(v.getContext(), "pw와 pwchk가 다릅니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(v.getContext(), "4~16자리를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"중복 확인을 먼저 해주세요.",Toast.LENGTH_SHORT).show();
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
    public static boolean validateId(String idStr) {
        Matcher matcher = VALID_ID_REGEX_ALPHA_NUM.matcher(idStr); return matcher.matches();
    }

}