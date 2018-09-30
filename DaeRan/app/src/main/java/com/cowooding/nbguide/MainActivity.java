package com.cowooding.nbguide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.cowooding.nbguide.TapPage.PlayListPage.PlayList;
import com.cowooding.nbguide.TapPage.InfoPage.Info;
import com.cowooding.nbguide.TapPage.MainPage.Main;
import com.cowooding.nbguide.TapPage.MyPage.LoginMyPage;
import com.cowooding.nbguide.TapPage.MyPage.MyPage;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends FragmentActivity {

    private InterstitialAd interstitialAd;
    AdRequest adRequest;


    String id;
    String loginId, loginPwd;
    int login=0;

    private long pressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, Main.newInstance()).commit();

        //만약 로그인 기록이 있다면 로그인 자체가 어렵기 때문에 굳이 여기서 빡세게 돌릴 필요 없이 유무만 확인
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);

        if(loginId !=null && loginPwd != null) {
            login=1;
            id=loginId;
        }
        replaceFragment(Main.newInstance());

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2955863180824800/2283003206");

        adRequest = new AdRequest.Builder().addTestDevice("1A6F26748DB789BFFD7C97C18BD4A7B5").build();

        interstitialAd.loadAd(adRequest);
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
                    replaceFragment(PlayList.newInstance());
                    return true;
                case R.id.action_three:
                    replaceFragment(Info.newInstance());
                    return true;
                case R.id.action_four:
                    if(login==1) { //로그인 성공시
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

        if(login==1){
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            fragment.setArguments(bundle);
        }

    }

    public interface onKeyBackPressedListener {
        public void onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBack();
        } else {


            //이건 누르면 종료 물어보는 코드 (여기에 광고 노출 가능)
            AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
            alert_ex.setTitle("종료하시겠습니까?");
            alert_ex.setMessage("정말로 종료하시겠습니까?");
            alert_ex.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 내용(취소시 할 일이 없기 때문에 아무일도 하지 않게 아무것도 적지
                }
            });
            alert_ex.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alert = alert_ex.create();
            alert.show();



            /* 이건 두번 누르면 종료되는 코드
            if (pressedTime == 0) {
                Toast.makeText(getApplicationContext(),
                        " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                pressedTime = System.currentTimeMillis();
            } else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if (seconds > 2000) {
                    Toast.makeText(getApplicationContext(),
                            " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                    pressedTime = 0;
                } else {
                    super.onBackPressed();
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }*/
        }

    }

    private void showInterstitial() {

        if (interstitialAd.isLoaded()) {

            interstitialAd.show();
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A6F26748DB789BFFD7C97C18BD4A7B5").build();
            interstitialAd.loadAd(adRequest);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
