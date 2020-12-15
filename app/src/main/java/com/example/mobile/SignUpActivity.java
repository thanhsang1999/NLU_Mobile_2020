package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {

    //pattern password
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    //email pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                            "\\@" +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                            "(" +
                                "\\." +
                                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                            ")+"
            );
    EditText editTextFullName;
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextEmail;
    EditText editTextConfirmPassword;
    ConnectionWebService connectionWebService;
    private ProgressBar spinner;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        connectionWebService = new ConnectionWebService(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        as();
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
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //validate

        //validate Fullname
        editTextFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextFullName.getText().length() == 0){
                    editTextFullName.setError("Fullname can't be empty");
                }else{
                    editTextFullName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //validate Username
        editTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextUserName.getText().length() == 0){
                    editTextUserName.setError("Username can't be empty");
                }
                else if(editTextUserName.getText().length() < 5){
                    editTextUserName.setError("Username too short");
                }else if(editTextUserName.getText().length() >= 15){
                    editTextUserName.setError("Username too long");
                }else{
                    editTextUserName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //validate Email
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextEmail.getText().length() == 0){
                    editTextEmail.setError("Email can't be empty");
                }else if(!EMAIL_PATTERN.matcher(editTextEmail.getText()).matches()){
                    editTextEmail.setError("Please enter a valid email address");
                }else{
                    editTextEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //validate Password
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextPassword.getText().length() == 0){
                    editTextPassword.setError("Password can't be empty");
                }else if(!PASSWORD_PATTERN.matcher(editTextPassword.getText()).matches()){
                    editTextPassword.setError("Please enter a valid password");
                }else{
                    editTextPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //validate confirmPassword
            editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String password = editTextPassword.getText().toString();
                    String cofirm = editTextConfirmPassword.getText().toString();
                    if(editTextConfirmPassword.getText().length() == 0) {
                        editTextConfirmPassword.setError("Password can't be empty");
                    }
                    else if(password.compareTo(cofirm) != 0){
                        editTextConfirmPassword.setError("Please enter a valid Password");
                    }
                    else {
                        editTextConfirmPassword.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        //end validate



        Button btn_sign_up=findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg="";
                String password = editTextPassword.getText().toString();
                String confirm= editTextConfirmPassword.getText().toString();
                if(password.isEmpty()|| confirm.isEmpty() ||password.compareTo(confirm)!=0){
                    msg="Password is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }else if(!PASSWORD_PATTERN.matcher(password).matches()){
                    msg="Please enter a valid password";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                String username= editTextUserName.getText().toString();
                if(username.isEmpty()){
                    msg="Username is invalid";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;

                }else if(username.length() < 5){
                    msg="Username too short";
                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }else if(username.length() >15){
                    msg="Username too long";
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

                }else if(!EMAIL_PATTERN.matcher(username).matches()){
                    msg="Please enter a valid email address";
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
    public void as(){
        backArrow=(ImageView) findViewById(R.id.backArrow);
        editTextFullName=findViewById(R.id.editTextFullName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextUserName=findViewById(R.id.editTextUserName);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextConfirmPassword=findViewById(R.id.editTextConfirmPassword);
    }



    public void loading(View view){
        spinner.setVisibility(View.VISIBLE);
    }
    public void loading_complete(View view){
        spinner.setVisibility(View.INVISIBLE);
    }
}