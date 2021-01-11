package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

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
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.mobile",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("123", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
        }

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
                Intent intent = new Intent(LogInActivity.this, ForgotPassActivity.class);
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