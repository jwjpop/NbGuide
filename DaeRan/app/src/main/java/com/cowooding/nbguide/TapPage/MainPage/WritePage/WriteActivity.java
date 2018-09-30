package com.cowooding.nbguide.TapPage.MainPage.WritePage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cowooding.nbguide.DB.InfoClass;
import com.cowooding.nbguide.MainActivity;
import com.cowooding.nbguide.R;
import com.cowooding.nbguide.TapPage.MainPage.BoardClass.BoardClass;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woojinroom on 2018-04-23.
 */

public class WriteActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private InterstitialAd interstitialAd;
    AdRequest adRequest;

    public static final Pattern NUMBER = Pattern.compile("^[0-9]{1,4}$");

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    Toolbar toolbar;
    TextView toolbar_title;
    ImageButton imageButtonLeft;
    ImageButton imageButtonRight;

    BoardClass board ;

    String st_color;
    Spinner color;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<String> arr_spin;

    EditText title,number,price,content;


    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().addTestDevice("1A6F26748DB789BFFD7C97C18BD4A7B5").build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2955863180824800/2283003206");



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("글 작성");

        title = (EditText)findViewById(R.id.edit_title);
        title.setSelected(false);
        number =(EditText)findViewById(R.id.edit_number);
        number.setSelected(false);
        color = (Spinner) findViewById(R.id.spinner_color);
        color.setSelected(false);
        price = (EditText)findViewById(R.id.edit_price);
        price.setSelected(false);
        content = (EditText)findViewById(R.id.edit_content);
        content.setSelected(false);

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });

        //색 받아옴
        arr_spin = new ArrayList<String>();

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("color"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr_spin.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    st_color = messageData.getValue().toString();
                    arr_spin.add(st_color);
                }
                spinnerAdapter = new ArrayAdapter(getApplicationContext(),R.layout.custom_simple_dropdown_item_1line,arr_spin);
                color.setAdapter(spinnerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().equals("")) {
                    if (validateNumber(number.getText().toString())) {
                        if (validateNumber(price.getText().toString())) {
                            if(!content.getText().toString().equals("")) {
                                Intent user = getIntent();

                                //사용자 계정 넘겨주는 부분 수정
                                board = new BoardClass(title.getText().toString(), getTime(), color.getSelectedItem().toString(),
                                        number.getText().toString(), price.getText().toString(), content.getText().toString(),
                                        user.getStringExtra("id"));

                                databaseReference.child("board").child(getTime() + "_" + user.getStringExtra("id")).setValue(board);
                                // databaseReference.child("user").child(user.getStringExtra("id")+"/write").setValue("1");

                                //광고 출력
                                interstitialAd.loadAd(adRequest);
                                interstitialAd.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdClosed() {

                                    }

                                    @Override
                                    public void onAdLoaded() {
                                        showInterstitial();
                                    }

                                    @Override
                                    public void onAdFailedToLoad(int errorCode) {

                                    }
                                });

                                Toast.makeText(getApplicationContext(), "작성 완료", Toast.LENGTH_SHORT).show();
                                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(refresh_intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "가격은 숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "드링크 수는 숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public static boolean validateNumber(String Str) {
        Matcher matcher = NUMBER.matcher(Str); return matcher.matches();
    }

    public void onBackPressed() {
        Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(refresh_intent);
        finish();
    }
    private void showInterstitial() {

        if (interstitialAd.isLoaded()) {

                            interstitialAd.show();
                            AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A6F26748DB789BFFD7C97C18BD4A7B5").build();
                            interstitialAd.loadAd(adRequest);
        }
    }
}
