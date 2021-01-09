package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobile.R;

public class ForgotPass3Activity extends AppCompatActivity {
    ImageView backArrow;
    Button btn_finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass3);
        as();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPass3Activity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    public void as(){
        backArrow=findViewById(R.id.backArrow);
        btn_finish=findViewById(R.id.btn_finish);
    }
}