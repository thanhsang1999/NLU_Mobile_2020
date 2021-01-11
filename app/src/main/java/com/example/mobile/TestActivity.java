package com.example.mobile;



import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.model.Account;
import com.example.mobile.utils.ExactThreadHelper;
import com.example.mobile.webservice.ultils.ExecuteRawSQLFromWebServie;
import com.example.mobile.webservice.ultils.IRawSQL;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TestActivity extends AppCompatActivity implements IRawSQL {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btn=findViewById(R.id.button);
        Button btn1=findViewById(R.id.button1);
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

                ExecuteRawSQLFromWebServie.executeQueryRaw("select id from tblaccount", TestActivity.this, new IRawSQL(){

                    @Override
                    public void acceptReturnRowAffect(int rowAffect) {

                    }

                    @Override
                    public void acceptReturnJSONArray(JSONArray jsonArray) throws JSONException {
                        if (jsonArray.length() != 1) {
                            String msg = "Tài khoản không hợp lệ.";
                            Log.e("len", jsonArray.length()+"");
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("user_properties");

                                Log.e("id"+i, jsonObject.getString("id"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });


            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ExecuteRawSQLFromWebServie.executeUpdateRaw("update tblaccount set password = '1111'", TestActivity.this, new IRawSQL() {
                    @Override
                    public void acceptReturnRowAffect(int rowAffect) {
                        Log.e("rowAffect",rowAffect+"");
                    }

                    @Override
                    public void acceptReturnJSONArray(JSONArray jsonArray) throws JSONException {

                    }
                });

            }
        });




    }


    @Override
    public void acceptReturnRowAffect(int rowAffect) {

    }

    @Override
    public void acceptReturnJSONArray(JSONArray jsonArray)  {

    }
}