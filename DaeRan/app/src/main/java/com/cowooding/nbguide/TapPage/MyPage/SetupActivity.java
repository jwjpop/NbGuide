package com.cowooding.nbguide.TapPage.MyPage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import com.cowooding.nbguide.R;

public class SetupActivity extends AppCompatActivity {

    ToggleButton toggle_alarm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

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

                    alarm.putString("agree", "true");
                    alarm.commit();
                    toggle_alarm.setBackgroundDrawable(getResources().getDrawable(R.drawable.alarm_on));

                }
                else{

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
