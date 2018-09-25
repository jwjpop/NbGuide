package com.cowooding.nbguide.TapPage.MyPage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cowooding.nbguide.R;

public class SetupActivity extends AppCompatActivity {

    ToggleButton toggle_alarm;

    Toolbar toolbar;
    TextView toolbar_title;

    ImageButton imageButtonLeft,imageButtonRight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("설정");

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    }
    public void onBackPressed() {
        finish();
    }
}
