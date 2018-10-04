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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by woojin on 2018-08-13.
 */

public class DeleteAccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView text_toolbar;

    ImageButton imageButtonLeft,imageButtonRight;
    EditText editText_pwnow;

    String pwnow,new_id;

    private FirebaseAuth mAuth;
    FirebaseUser user;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteaccount);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A6F26748DB789BFFD7C97C18BD4A7B5").build();
        mAdView.loadAd(adRequest);

        //디비용 아이디
        Intent intent = getIntent();
        new_id = intent.getStringExtra("new_id");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        text_toolbar = (TextView)toolbar.findViewById(R.id.title);
        text_toolbar.setText("계정 삭제");

        editText_pwnow = (EditText)findViewById(R.id.edit_pwnow);

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

                if (!pwnow.equals("")) {
                    //firebase auth 참조
                    mAuth = FirebaseAuth.getInstance();
                    //현재 유저 정보 받아옴
                    user = mAuth.getCurrentUser();
                    //현재 정보 확인
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), pwnow);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //현재 비밀번호 일치 할 경우
                            if (task.isSuccessful()) {
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    mDatabase = FirebaseDatabase.getInstance();
                                                    mReference = mDatabase.getReference("user/" + new_id);
                                                    mReference.removeValue();

                                                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = auto.edit();
                                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                                    editor.clear();
                                                    editor.commit();
                                                    //뷰가 불안정할 것
                                                    Toast.makeText(getApplicationContext(),"회원 탈퇴 완료",Toast.LENGTH_SHORT).show();
                                                    Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
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
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
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
}
