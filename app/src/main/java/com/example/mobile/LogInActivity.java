package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class LogInActivity extends AppCompatActivity {
    ConnectionDatabaseLocalMobile connectionDatabaseLocalMobile;
    EditText editTextUserName;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        editTextUserName=findViewById(R.id.editTextUserName);
        editTextPassword=findViewById(R.id.editTextPassword);
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
        connectionDatabaseLocalMobile = new ConnectionDatabaseLocalMobile(this);
        Button btn_log_in=findViewById(R.id.btn_log_in);
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
//                String msg="Error";
//
//                String password = editTextPassword.getText().toString();
//
//                if(password.isEmpty()){
//                    msg="Password is invalid";
//                    Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    return;
//
//                }
//                String username= editTextUserName.getText().toString();
//                if(username.isEmpty()){
//                    msg="Username is invalid";
//                    Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    return;
//
//                }
//                try {
//                    if(LogInActivity.this.connectionDatabaseLocalMobile.login(username, password)){
//                        msg="OK";
//                    }
//                }
//                catch (Exception e){
//
//                }
//                Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void getdata(){
        String url="http://192.168.1.17/mobile/login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(LogInActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LogInActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
}