package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    ConnectionWebService connectionWebService;
    EditText editTextUserName;
    EditText editTextPassword;
    private ProgressBar spinner;
    ImageView backArrow;
    Button btn_log_in;
    TextView textViewForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        as();
        backArrow=findViewById(R.id.backArrow);

        spinner.setVisibility(View.GONE);
        TextView textViewSignUp=findViewById(R.id.textViewSignUp);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotPass1Activity.class);
                startActivity(intent);
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        connectionWebService = new ConnectionWebService(this);
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String msg="Error";

                String password = editTextPassword.getText().toString();

                if(password.isEmpty()){
                    msg="Password is invalid";
                    Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String username= editTextUserName.getText().toString();
                if(username.isEmpty()){
                    msg="Username is invalid";
                    Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                try {
                    LogInActivity.this.connectionWebService.login(username, password);
                }
                catch (Exception e){

                }

            }
        });


    }
    public void as(){
        editTextUserName=findViewById(R.id.editTextUserName);
        editTextPassword=findViewById(R.id.editTextPassword);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        textViewForgot=findViewById(R.id.textViewForgot);
        btn_log_in=findViewById(R.id.btn_log_in);
    }

    public void loading(View view){
        spinner.setVisibility(View.VISIBLE);
    }
    public void loading_complete(View view){
        spinner.setVisibility(View.INVISIBLE);
    }

}