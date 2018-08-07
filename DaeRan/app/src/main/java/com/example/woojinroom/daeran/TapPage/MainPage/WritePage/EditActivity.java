package com.example.woojinroom.daeran.TapPage.MainPage.WritePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woojinroom.daeran.MainActivity;
import com.example.woojinroom.daeran.R;
import com.example.woojinroom.daeran.TapPage.MainPage.BoardClass.BoardClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    BoardClass board ;

    Spinner color;
    EditText title,number,price,content;

    Intent edit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edit = getIntent();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.title);
        toolbar_title.setText("글 수정");

        title = (EditText)findViewById(R.id.edit_title);
        number =(EditText)findViewById(R.id.edit_number);
        color = (Spinner) findViewById(R.id.spinner_color);
        price = (EditText)findViewById(R.id.edit_price);
        content = (EditText)findViewById(R.id.edit_content);

        //인텐트 받아온것을 뿌려줌
        title.setText(edit.getStringExtra("title"));
        number.setText(edit.getStringExtra("number"));
        price.setText(edit.getStringExtra("price"));
        content.setText(edit.getStringExtra("content"));

        imageButtonLeft = (ImageButton) toolbar.findViewById(R.id.imagebutton_left);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"수정 취소",Toast.LENGTH_SHORT).show();
                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });


        imageButtonRight = (ImageButton) toolbar.findViewById(R.id.imagebutton_right);
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //title.getText 를 반납
                board = new BoardClass(title.getText().toString(),edit.getStringExtra("date"),color.getSelectedItem().toString(),
                        number.getText().toString(),price.getText().toString(),content.getText().toString(),
                        edit.getStringExtra("user"));

                //수정할때는 시간 초기화하지 않음
                databaseReference.child("board").child(edit.getStringExtra("user")+edit.getStringExtra("date")).setValue(board);

                Toast.makeText(getApplicationContext(),"수정 완료",Toast.LENGTH_SHORT).show();
                Intent refresh_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh_intent);
                finish();
            }
        });
    }

}
