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
import android.view.KeyEvent;
import android.view.MenuItem;

import com.cowooding.nbguide.TapPage.PlayListPage.PlayList;
import com.cowooding.nbguide.TapPage.InfoPage.Info;
import com.cowooding.nbguide.TapPage.MainPage.Main;
import com.cowooding.nbguide.TapPage.MyPage.LoginMyPage;
import com.cowooding.nbguide.TapPage.MyPage.MyPage;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends FragmentActivity implements CaulyCloseAdListener {

    private static final String APP_CODE = "7CAwpkVP"; // 광고 요청을 코드 ( 자신의 APP_CODE 쓸 것 )
    CaulyCloseAd mCloseAd ;

    AdRequest adRequest;

    String id;
    String loginId, loginPwd;
    int login=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder(APP_CODE).build();
        mCloseAd = new CaulyCloseAd();


    //원하는 버튼의 문구를 설정 할 수 있다.
    mCloseAd.setButtonText("취소", "종료");
    //원하는 텍스트의 문구를 설정 할 수 있다.
    mCloseAd.setDescriptionText("종료하시겠습니까?");

        mCloseAd.setAdInfo(closeAdInfo);
        mCloseAd.setCloseAdListener(this); // CaulyCloseAdListener 등록
        // 종료광고 노출 후 back버튼 사용을 막기 원할 경우 disableBackKey();을 추가한다
         mCloseAd.disableBackKey();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 앱을 처음 설치하여 실행할 때, 필요한 리소스를 다운받았는지 여부.
            if (mCloseAd.isModuleLoaded())
            {
                mCloseAd.show(this);
            }
            else
            {
                // 광고에 필요한 리소스를 한번만  다운받는데 실패했을 때 앱의 종료팝업 구현
                showDefaultClosePopup();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDefaultClosePopup()
    {
        new AlertDialog.Builder(this).setTitle("").setMessage("종료 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("아니요",null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCloseAd != null)
            mCloseAd.resume(this); // 필수 호출
    }

    @Override
    public void onReceiveCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onShowedCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onFailedToReceiveCloseAd(CaulyCloseAd caulyCloseAd, int i, String s) {

    }

    @Override
    public void onLeftClicked(CaulyCloseAd caulyCloseAd) {

    }

    @Override
    public void onRightClicked(CaulyCloseAd caulyCloseAd) {
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onLeaveCloseAd(CaulyCloseAd caulyCloseAd) {

    }

}
