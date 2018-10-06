package com.cowooding.nbguide.TapPage.MyPage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cowooding.nbguide.MainActivity;
import com.cowooding.nbguide.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SetupActivity extends AppCompatActivity {

    Button button_editaccount,button_deleteaccount;
    ToggleButton toggle_alarm;

    String new_id;

    Toolbar toolbar;
    TextView toolbar_title;

    ImageButton imageButtonLeft,imageButtonRight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("설정");

        Intent intent = getIntent();
        new_id = intent.getStringExtra("new_id");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh_intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setVisibility(View.INVISIBLE);

        toggle_alarm = (ToggleButton)findViewById(R.id.toggle_alarm);

        SharedPreferences agree = getSharedPreferences("agree", Activity.MODE_PRIVATE);
        String  st_agree = agree.getString("agree","true");

        //토글 상태 검사
        if(st_agree.toString().equals("true")){
            toggle_alarm.setChecked(true);
            toggle_alarm.setBackgroundDrawable(getResources().getDrawable(R.drawable.alarm_on));
        }
        else {
            toggle_alarm.setChecked(false);
            toggle_alarm.setBackgroundDrawable(getResources().getDrawable(R.drawable.alarm_off));
        }

        //토글 버튼 눌리면
        toggle_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences agree = getSharedPreferences("agree", Activity.MODE_PRIVATE);
                SharedPreferences.Editor alarm = agree.edit();
                if(toggle_alarm.isChecked()){
                    //알람 수신
                    alarm.putString("agree", "true");
                    alarm.commit();
                    toggle_alarm.setBackgroundDrawable(getResources().getDrawable(R.drawable.alarm_on));

                }
                else{
                    //알람 미수신
                    alarm.putString("agree", "false");
                    alarm.commit();
                    toggle_alarm.setBackgroundDrawable(getResources().getDrawable(R.drawable.alarm_off));


                }
            }
        });

        button_editaccount=(Button)findViewById(R.id.button_edit_account);
        button_editaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_edit = new Intent(getApplicationContext(),EditAccountActivity.class);
                intent_edit.putExtra("new_id",new_id);
                startActivity(intent_edit);
                finish();
            }
        });

        button_deleteaccount=(Button)findViewById(R.id.button_delete_account);
        button_deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent delete_intent = new Intent(getApplicationContext(),DeleteAccountActivity.class);
                delete_intent.putExtra("new_id",new_id);
                startActivity(delete_intent);
                finish();
            }
        });

    }
    public void onBackPressed() {
        Intent refresh_intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(refresh_intent);
        finish();
    }
}
