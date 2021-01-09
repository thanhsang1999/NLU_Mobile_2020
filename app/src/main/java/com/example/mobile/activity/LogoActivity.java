package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mobile.Config;
import com.example.mobile.R;
import com.example.mobile.webservice.ultils.MyWorker;
import com.example.mobile.webservice.ultils.PrepareConnectionWebService;

import java.util.HashMap;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Thread(new Runnable() {
            public void run() {

                //Thread.sleep(2000);
                MyWorker myWorker= new MyWorker();
                myWorker.setActivity(LogoActivity.this);
                myWorker.setError(()->{
                    String msg = "Kết nối mạng bị lỗi.";
                    Log.e("Error", myWorker.getErrorMessengr());

                    Toast.makeText( myWorker.getActivity(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( myWorker.getActivity(), HomeActivity.class);
                    myWorker.getActivity().startActivity(intent);
                    myWorker.getActivity().finish();
                });
                myWorker.setSuccess(()->{
                    Log.e("Check Service Alive",myWorker.getResponse() );

                    if(myWorker.getResponse().equals("alive")){
                        Log.e("Connection", "OK");
                        Intent intent = new Intent( myWorker.getActivity(), WellComeActivity.class);
                        myWorker.getActivity().startActivity(intent);
                        myWorker.getActivity().finish();
                    }
                });
                myWorker.setParams(new HashMap<String,String>(){{
                    put("","");
                }});

                PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "alive.php");



            }
        }).start();



    }
}