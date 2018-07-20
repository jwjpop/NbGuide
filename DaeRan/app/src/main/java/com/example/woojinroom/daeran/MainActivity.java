package com.example.woojinroom.daeran;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.woojinroom.daeran.TapPage.DJPage.Dj;
import com.example.woojinroom.daeran.TapPage.InfoPage.Info;
import com.example.woojinroom.daeran.TapPage.MainPage.Main;
import com.example.woojinroom.daeran.TapPage.MyPage.LoginMyPage;
import com.example.woojinroom.daeran.TapPage.MyPage.MyPage;


public class MainActivity extends FragmentActivity {

    Intent intent;
    int value=0;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, Main.newInstance()).commit();

        intent = getIntent();
        if(intent!=null) {
            value = intent.getIntExtra("value", 0);
            id = intent.getStringExtra("id"); //로그인한 아이디

        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_one:
                    replaceFragment(Main.newInstance());
                    return true;
                case R.id.action_two:
                    replaceFragment(Dj.newInstance());
                    return true;
                case R.id.action_three:
                    replaceFragment(Info.newInstance());
                    return true;
                case R.id.action_four:
                    if(value==1) { //로그인 성공시

                        replaceFragment(LoginMyPage.newInstance());

                    } else { //로그아웃 또는 비 로그인시
                        replaceFragment(MyPage.newInstance());
                    }
                    return true;
            }
            return false;
        }

    };

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();

        if(value==1){
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            fragment.setArguments(bundle);
        }

    }
}
