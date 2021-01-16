package com.example.mobile.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
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

import com.example.mobile.ConnectionWebService;
import com.example.mobile.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LogInActivity extends AppCompatActivity {

    private ConnectionWebService connectionWebService;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private ProgressBar spinner;
    private ImageView backArrow;
    private Button btn_log_in;
    private Button btn_skip;
    private TextView textViewForgot;
    private TextView textViewSignUp;
    private Button login_button;

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        matchIdElement();
        connectionWebService = new ConnectionWebService(this);
        addListener();
        navigationBackPressed();
    }

    private void navigationBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(LogInActivity.this, LogoActivity.class);
                startActivity(intent);
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void addListener() {
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String msg = "Error";

                String password = editTextPassword.getText().toString();

                if (password.isEmpty()) {
                    msg = "Password is invalid";
                    Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String username = editTextUserName.getText().toString();
                if (username.isEmpty()) {
                    msg = "Username is invalid";
                    Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                try {
                    LogInActivity.this.connectionWebService.login(username, password);
                } catch (Exception e) {

                }

            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotPassActivity.class);
                startActivity(intent);
                finish();
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, LogoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        callbackManager = CallbackManager.Factory.create();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LogInActivity.this,
                        Arrays.asList("public_profile","email","user_birthday","user_gender"));
            loginFacebook();
            }
        });
    }

    public void matchIdElement() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        textViewForgot = findViewById(R.id.textViewForgot);
        btn_log_in = findViewById(R.id.btn_log_in);
        btn_skip = findViewById(R.id.btn_skip);
        backArrow = findViewById(R.id.backArrow);
        spinner.setVisibility(View.GONE);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        login_button = findViewById(R.id.login_button);
    }

    public void loading(View view) {
        spinner.setVisibility(View.VISIBLE);
    }

    public void loading_complete(View view) {
        spinner.setVisibility(View.INVISIBLE);
    }
    private void loginFacebook(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("a","thành công");
                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d("a","thoát");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("a","lỗi");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}