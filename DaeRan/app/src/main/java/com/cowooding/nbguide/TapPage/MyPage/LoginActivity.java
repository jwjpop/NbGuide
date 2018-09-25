package com.cowooding.nbguide.TapPage.MyPage;

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
import android.widget.TextView;
import android.widget.Toast;

import com.cowooding.nbguide.MainActivity;
import com.cowooding.nbguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by woojin on 2018-07-15.
 */

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    Toolbar toolbar;
    TextView text_toolbar;

    ImageButton imageButtonLeft,imageButtonRight;
    EditText editText_id,editText_pw;
    String id,pw;
    String equal_id,equal_pw;

    Button button_signUp;

    int id_count=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        text_toolbar = (TextView)toolbar.findViewById(R.id.title);
        text_toolbar.setText("로그인");

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });

        editText_id = (EditText)findViewById(R.id.edit_id);
        editText_pw = (EditText)findViewById(R.id.edit_pw);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("user"); // 변경값을 확인할 child 이름

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_count = 0;

                id = editText_id.getText().toString();
                pw = editText_pw.getText().toString();

                if (!id.equals("")) { // 공백이 아닌 경우
                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                UserClass db_user = messageData.getValue(UserClass.class);
                                // child 내에 있는 데이터만큼 반복합니다.
                                if (id.equals(db_user.getId())) {
                                    id_count++;
                                    equal_id=db_user.getId();
                                    equal_pw=db_user.getPw();
                                    break;
                                }
                            }
                            if (id_count != 0) { // 계정이 있는 경우
                                if(pw.equals(equal_pw)){ // 비밀번호가 일치하는 경우

                                    //유저가 토큰이 변경 될 만한 행동을 한 경우 로그인 할 때 토큰 초기화해줌
                                    String token = FirebaseInstanceId.getInstance().getToken();
                                    UserClass userClass = new UserClass(id, pw,"0",token);
                                    databaseReference.child("user").child(id).setValue(userClass);

                                    SharedPreferences agree = getSharedPreferences("agree", Activity.MODE_PRIVATE);
                                    String  st_agree = agree.getString("agree","null");
                                    if(st_agree.equals("null")){
                                        SharedPreferences.Editor alarm = agree.edit();
                                        alarm.putString("agree", "true");
                                        alarm.commit();
                                    }
                                    //최초 로그인시에 알림 동의 / 재 로그인시에 알림 값 설정하려면 디비에 넣어놔야 할 듯
                                   /* SharedPreferences agree = getSharedPreferences("agree", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor alarm = agree.edit();
                                    alarm.putString("agree", "true");
                                    alarm.commit();*/

                                    Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();

                                    //로그인 정보 저장
                                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor autoLogin = auto.edit();
                                    autoLogin.putString("inputId", id);
                                    autoLogin.putString("inputPwd", pw);
                                    autoLogin.commit();

                                    Intent login_intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(login_intent);
                                    finish();
                                } else{ //아이디는 맞지만 비밀번호가 다른 경우
                                    Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            } else { // 계정이 없는 경우
                                Toast.makeText(getApplicationContext(), "계정을 확인해주세요", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{ // 공백인 경우
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button_signUp=(Button)findViewById(R.id.button_signUp);
        button_signUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent_signUp = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent_signUp);
                finish();
            }
        });
    }
    public void onBackPressed() {
        Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(refresh_intent);
        finish();
    }
}
