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
    private FirebaseAuth mAuth;

    Toolbar toolbar;
    ImageButton imageButtonLeft,imageButtonRight;
    EditText editText_id,editText_pw,editText_pwchk;
    String id,pw,pwchk;
    Button button_equals;

    int id_count=0;
    int id_chk=0;
    int sign=0;

    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAuth = FirebaseAuth.getInstance();

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
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
                        String split_id[] = id.split("\\.");
                        final String new_id = split_id[0] + "_" + split_id[1];
                        mReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                    UserClass db_user = messageData.getValue(UserClass.class);
                                    // child 내에 있는 데이터만큼 반복합니다.
                                    if (new_id.equals(db_user.getId())) {
                                        id_count++;
                                    }
                                }
                                if(sign==0){
                                    if (id_count != 0) {
                                        Toast.makeText(getApplicationContext(), "중복된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                        id_chk = 1;
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "이메일 형식으로 작성해주세요.", Toast.LENGTH_SHORT).show();
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

                            mAuth.createUserWithEmailAndPassword(id,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"계정 생성 성공",Toast.LENGTH_SHORT).show();
                                        //가입 완료 후 중복 체크 방지
                                        sign=1;

                                        //토큰 받기
                                        String token = FirebaseInstanceId.getInstance().getToken();

                                        //파이어베이스에 .이 들어갈 수 없어서 언더바로 대체
                                        //이메일까지 저장해야 asdf@naver_com 과 asdf@daum_net 이 다른 유저임을 식별 가능
                                        String split_id[] = id.split("\\.");
                                        String new_id = split_id[0]+"_"+split_id[1];
                                        UserClass userClass = new UserClass(new_id,token);
                                        databaseReference.child("user").child(new_id).setValue(userClass);

                                        //최초 회원가입시에 알림 동의
                                        SharedPreferences agree = getSharedPreferences("agree", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor alarm = agree.edit();
                                        alarm.putString("agree", "true");
                                        alarm.commit();

                                        //로그인 정보 저장
                                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor autoLogin = auto.edit();
                                        autoLogin.putString("inputId", id);
                                        autoLogin.putString("inputPwd", pw);
                                        autoLogin.commit();

                                        Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(refresh_intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"계정 생성 실패",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(v.getContext(), "pw와 pwchk가 다릅니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(v.getContext(), "6~16자리를 입력해주세요", Toast.LENGTH_SHORT).show();
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
        Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(refresh_intent);
        finish();
    }

    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr); return matcher.matches();
    }
}