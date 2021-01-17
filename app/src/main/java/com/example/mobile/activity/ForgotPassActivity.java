package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobile.database.webservice.ConnectionWebService;
import com.example.mobile.R;


public class ForgotPassActivity extends AppCompatActivity {
    ConnectionWebService connectionWebService;

    ImageView backArrow;
    Button btn_next;
    EditText editTextEmail;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        connectionWebService = new ConnectionWebService(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass1);
        as();
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg="";
                String sendEmail = editTextEmail.getText().toString();

                if(sendEmail.isEmpty()){
                    msg="Email is invalid";
                    Toast.makeText(ForgotPassActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    ForgotPassActivity.this.connectionWebService.forgotPassword(sendEmail);
                }
                catch (Exception e){

                }


            }
        });



    }
    public void as(){
        backArrow=findViewById(R.id.backArrow);
        btn_next=findViewById(R.id.btn_next);
        editTextEmail=findViewById(R.id.editTextEmail);
    }

    public void loading(View view){
        spinner.setVisibility(View.VISIBLE);
    }
    public void loading_complete(View view){
        spinner.setVisibility(View.INVISIBLE);
    }

}