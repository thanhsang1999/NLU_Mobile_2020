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

import com.example.mobile.model.Account;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextFullName;
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextEmail;
    EditText editTextConfirmPassword;
    ConnectionWebService connectionWebService;
    private ProgressBar spinner;
    ImageView backArrow;
    TextView textViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        connectionWebService = new ConnectionWebService(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        matchIdElement();
        spinner.setVisibility(View.GONE);
        addListener();
        navigationBackPressed();


    }

    private void navigationBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(SignUpActivity.this, LogoActivity.class);
                startActivity(intent);
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void matchIdElement() {
        backArrow = (ImageView) findViewById(R.id.backArrow);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        textViewSignIn = findViewById(R.id.textViewSignIn);
    }

    public void addListener() {
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = "";
                String password = editTextPassword.getText().toString();
                String confirm = editTextConfirmPassword.getText().toString();
                if (password.isEmpty() || password.compareTo(confirm) != 0) {
                    msg = "Password is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String username = editTextUserName.getText().toString();
                if (username.isEmpty()) {
                    msg = "Username is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String fullname = editTextFullName.getText().toString();
                if (fullname.isEmpty()) {
                    msg = "Fullname is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String email = editTextEmail.getText().toString();
                if (email.isEmpty()) {
                    msg = "Email is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                Account account = new Account(username, fullname, email, password);
                ;
                msg = "Error";
                try {
                    SignUpActivity.this.connectionWebService.insert_account(account);

                } catch (Exception e) {

                }


            }
        });
    }

    public void loading(View view) {
        spinner.setVisibility(View.VISIBLE);
    }

    public void loading_complete(View view) {
        spinner.setVisibility(View.INVISIBLE);
    }
}