package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity {
    ConnectionWebService connectionWebService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        connectionWebService = new ConnectionWebService(this);
        new Thread(new Runnable() {
            public void run() {
                try {
                    //Thread.sleep(2000);
                    connectionWebService.alive();

                }
                catch (Exception e){

                }

            }
        }).start();



    }
}