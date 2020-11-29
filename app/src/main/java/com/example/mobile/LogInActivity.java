package com.example.mobile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Intent intent = new Intent(LogInActivity.this, ForgotPass1Activity.class);
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
    }

    public void loading(View view) {
        spinner.setVisibility(View.VISIBLE);
    }

    public void loading_complete(View view) {
        spinner.setVisibility(View.INVISIBLE);
    }

}