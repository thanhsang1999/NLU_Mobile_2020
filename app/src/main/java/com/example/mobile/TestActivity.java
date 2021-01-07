package com.example.mobile;



import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.utils.ExactThreadHelper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TestActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btn=findViewById(R.id.button);
        TextView textView=findViewById(R.id.textView);
        Calendar c = Calendar.getInstance();

        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE+1));
        c.set(Calendar.SECOND,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DateFormat dateFormat= new SimpleDateFormat("hh:mm:ss");
            textView.setText(dateFormat.format(c.getTime())+"");
            Log.e("VErsion","run");
            ExactThreadHelper.hel(TestActivity.this, c);
        } else {
            Log.e("VErsion","not");
        }
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



            }
        });


    }




}