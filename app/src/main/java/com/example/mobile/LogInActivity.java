package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    ConnectionDatabase connectionDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        TextView textViewSignUp=findViewById(R.id.textViewSignUp);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        TextView textViewForgot=findViewById(R.id.textViewForgot);
        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });
        connectionDatabase = new ConnectionDatabase(this);
        Button btn_log_in=findViewById(R.id.btn_log_in);
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.connectionDatabase.insert_accounts(null);
                String msg="Error";
                try {
                    if(LogInActivity.this.connectionDatabase.login("1", "1")){
                        msg="OK";
                    }
                }
                catch (Exception e){

                }
                Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


    }
}