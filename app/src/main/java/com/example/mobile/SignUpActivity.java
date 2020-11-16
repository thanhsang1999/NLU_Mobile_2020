package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextFullName;
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextEmail;
    EditText editTextConfirmPassword;
    ConnectionWebService connectionWebService;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        connectionWebService = new ConnectionWebService(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        TextView textViewSignIn=findViewById(R.id.textViewSignIn);
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        editTextFullName=findViewById(R.id.editTextFullName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextUserName=findViewById(R.id.editTextUserName);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextConfirmPassword=findViewById(R.id.editTextConfirmPassword);

        Button btn_sign_up=findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String msg="";
                String password = editTextPassword.getText().toString();
                String confirm= editTextConfirmPassword.getText().toString();
                if(password.isEmpty()||password.compareTo(confirm)!=0){
                    msg="Password is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String username= editTextUserName.getText().toString();
                if(username.isEmpty()){
                    msg="Username is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String fullname= editTextFullName.getText().toString();
                if(fullname.isEmpty()){
                    msg="Fullname is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                String email= editTextEmail.getText().toString();
                if(email.isEmpty()){
                    msg="Email is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }
                Account account= new Account(username, fullname, email, password);
                ;
                msg="Error";
                try {
                    SignUpActivity.this.connectionWebService.insert_accounts(account);

                }
                catch (Exception e){

                }



            }
        });

    }
    public void loading(View view){
        spinner.setVisibility(View.VISIBLE);
    }
    public void loading_complete(View view){
        spinner.setVisibility(View.INVISIBLE);
    }
}