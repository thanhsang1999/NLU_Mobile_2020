package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobile.R;

public class ForgotPass1Activity extends AppCompatActivity {
    ImageView backArrow;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass1);
        as();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPass1Activity.this, ForgotPass2Activity.class);
                startActivity(intent);
            }
        });
    }
    public void as(){
        backArrow=findViewById(R.id.backArrow);
        btn_next=findViewById(R.id.btn_next);
    }

}