package com.cowooding.nbguide.TapPage.MainPage.WritePage;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.cowooding.nbguide.R;

public class AdsFull {

    private static InterstitialAd adFull;

    private static AdsFull instance = null;
    private static Context context;

    public AdsFull(Context context) {
        this.context = context;
    }

    public static AdsFull getInstance(Context context) {


        if (instance == null) {
            instance = new AdsFull(context);
            adFull = new InterstitialAd(context);
            setAds();
        }

        return instance;
    }


    private static void setAds() {

        AdRequest adRequest = new AdRequest.Builder().build(); //새 광고요청
        adFull = new InterstitialAd(context);
        adFull.setAdUnitId(context.getResources().getString(R.string.adID));
        adFull.loadAd(adRequest); //요청한 광고를 load 합니다.

        adFull.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() { //전면 광고가 열린 뒤에 닫혔을 때
                AdRequest adRequest = new AdRequest.Builder().build(); //새 광고요청
                adFull.loadAd(adRequest); //요청한 광고를 load 합니다.
            }
        });
    }


    public void show() {


                if (adFull == null)
                    setAds();
                adFull.show();

    }


}
