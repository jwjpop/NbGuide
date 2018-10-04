package com.cowooding.nbguide.TapPage.MyPage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    String pwnow,pwnew,pwnewagain,new_id;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        new_id = intent.getStringExtra("new_id");

        text_toolbar = (TextView)toolbar.findViewById(R.id.title);
        text_toolbar.setText("비밀번호 변경");

        editText_pwnow = (EditText)findViewById(R.id.edit_pwnow);
        editText_pwnew = (EditText)findViewById(R.id.edit_pwnew);
        getEditText_pwnewagain = (EditText)findViewById(R.id.edit_pwnewagain);

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_setup = new Intent(getApplicationContext(),SetupActivity.class);
                intent_setup.putExtra("new_id",new_id);
                startActivity(intent_setup);
                finish();
            }
        });



        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        //계정 비밀번호 수정하는 란인데 파이어베이스 Auth 알고리즘 쓰면 될 듯
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pwnow = editText_pwnow.getText().toString();
                pwnew = editText_pwnew.getText().toString();
                pwnewagain = getEditText_pwnewagain.getText().toString();

                if (!pwnow.equals("")&&!pwnew.equals("")&&!pwnewagain.equals("")) {

                    if(pwnew.equals(pwnewagain)){ //새 비밀번호끼리 일치한 경우

                        if(validatePassword(pwnew)) {
                            //firebase auth 참조
                            mAuth = FirebaseAuth.getInstance();
                            //현재 유저 정보 받아옴
                            user = mAuth.getCurrentUser();
                            //현재 정보 확인
                            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),pwnow);

                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //현재 비밀번호 일치 할 경우
                                    if(task.isSuccessful()){
                                        user.updatePassword(pwnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                                    SharedPreferences.Editor autoLogin = auto.edit();
                                                    autoLogin.putString("inputId", user.getEmail());
                                                    autoLogin.putString("inputPwd", pwnew);
                                                    autoLogin.commit();

                                                    Toast.makeText(getApplicationContext(),"비밀번호 변경 완료",Toast.LENGTH_SHORT).show();
                                                    Intent refresh_intent = new Intent(getApplicationContext(),MainActivity.class);
                                                    startActivity(refresh_intent);
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                    //현재 비밀번호 일치하지 않을 경우
                                    else{
                                        Toast.makeText(getApplicationContext(),"현재 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"6~16자리를 입력해주세요",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"새 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed() {
        Intent intent_setup = new Intent(getApplicationContext(),SetupActivity.class);
        intent_setup.putExtra("new_id",new_id);
        startActivity(intent_setup);
        finish();
    }

    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr); return matcher.matches();
    }
}
