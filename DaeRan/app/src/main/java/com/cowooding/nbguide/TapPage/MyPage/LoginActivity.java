package com.cowooding.nbguide.TapPage.MyPage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mAuth;

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

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A6F26748DB789BFFD7C97C18BD4A7B5").build();
        mAdView.loadAd(adRequest);

        mAuth = FirebaseAuth.getInstance();

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
                    mAuth.signInWithEmailAndPassword(id, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //유저가 토큰이 변경 될 만한 행동을 한 경우 로그인 할 때 토큰 초기화해줌
                                String token = FirebaseInstanceId.getInstance().getToken();
                                String split_id[] = id.split("\\.");
                                String new_id = split_id[0]+"_"+split_id[1];
                                UserClass userClass = new UserClass(new_id,token);
                                databaseReference.child("user").child(new_id).setValue(userClass);

                                //기기에 저장된 값 가져와서 null(한 번도 동의한 적 없는 경우)이면 true
                                //true 또는 false면 그대로 가져옴
                                SharedPreferences agree = getSharedPreferences("agree", Activity.MODE_PRIVATE);
                                String  st_agree = agree.getString("agree","null");
                                if(st_agree.equals("null")){
                                    SharedPreferences.Editor alarm = agree.edit();
                                    alarm.putString("agree", "true");
                                    alarm.commit();
                                }

                                //로그인 정보 저장
                                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = auto.edit();
                                autoLogin.putString("inputId", id);
                                autoLogin.putString("inputPwd", pw);
                                autoLogin.commit();


                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent login_intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(login_intent);
                                finish();
                            } else {
                                //로그인 실패시
                                mReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                            UserClass db_user = messageData.getValue(UserClass.class);
                                            // child 내에 있는 데이터만큼 반복합니다.
                                            String split_id[] = id.split("\\.");
                                            String new_id = split_id[0]+"_"+split_id[1];
                                            if (new_id.equals(db_user.getId())) {
                                                id_count++;
                                                equal_id=db_user.getId();
                                                break;
                                            }
                                        }
                                        if (id_count != 0) { // 계정이 있는 경우
                                            if(pw.equals(equal_pw)){ // 비밀번호가 일치하는 경우

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
                            }
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
