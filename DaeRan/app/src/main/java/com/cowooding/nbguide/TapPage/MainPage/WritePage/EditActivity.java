package com.cowooding.nbguide.TapPage.MainPage.WritePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cowooding.nbguide.MainActivity;
import com.cowooding.nbguide.R;
import com.cowooding.nbguide.TapPage.MainPage.Board.BoardClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woojinroom on 2018-04-23.
 */

public class EditActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    Toolbar toolbar;
    TextView toolbar_title;
    ImageButton imageButtonLeft;
    ImageButton imageButtonRight;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    BoardClass board ;

    String st_color;
    Spinner color;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<String> arr_spin;

    EditText title,number,price,content;

    Intent edit;

    public static final Pattern NUMBER = Pattern.compile("^[0-9]{1,4}$");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edit = getIntent();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("글 수정");

        title = (EditText)findViewById(R.id.edit_title);
        number =(EditText)findViewById(R.id.edit_number);
        color = (Spinner) findViewById(R.id.spinner_color);
        price = (EditText)findViewById(R.id.edit_price);
        content = (EditText)findViewById(R.id.edit_content);

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
        //인텐트 받아온것을 뿌려줌
        title.setText(edit.getStringExtra("title"));
        number.setText(edit.getStringExtra("number"));
        price.setText(edit.getStringExtra("price"));
        content.setText(edit.getStringExtra("content"));

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                //title.getText 를 반납
                board = new BoardClass(title.getText().toString(),edit.getStringExtra("date"),color.getSelectedItem().toString(),
                        number.getText().toString(),price.getText().toString(),content.getText().toString(),
                        edit.getStringExtra("user"));

                //수정할때는 시간 초기화하지 않음
                databaseReference.child("board").child(edit.getStringExtra("date")+"_"+edit.getStringExtra("user")).setValue(board);

                Toast.makeText(getApplicationContext(),"수정 완료",Toast.LENGTH_SHORT).show();
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

    public static boolean validateNumber(String Str) {
        Matcher matcher = NUMBER.matcher(Str); return matcher.matches();
    }

    public void onBackPressed() {
        finish();
    }

}
