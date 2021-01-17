package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mobile.R;
import com.example.mobile.database.sqlite.AccountDAO;
import com.example.mobile.model.Account;
import com.example.mobile.model.Tool;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    EditText editTextFullName,editTextEmail;
    ImageView image;
    TextView textViewTitle;
    Button btn_dOB,btn_save;
    RadioGroup radioGroupGender;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private ImageView backArrow;
    AccountDAO accountDAO;
    Account account;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        mathIdElement();

        btn_dOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date =day + "/" + month + "/"  + year;
                btn_dOB.setText(date);
            }
        };
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroupGender.getCheckedRadioButtonId()==R.id.radioButtonFemale)
                account.setGender(Account.GENDER_FEMALE);else
                if(radioGroupGender.getCheckedRadioButtonId()==R.id.radioButtonMale)
                    account.setGender(Account.GENDER_MALE);
                account.setFullname(editTextFullName.getText().toString());
                account.setDateOfBirth(Tool.StringToDateVN(btn_dOB.getText().toString()));
                account.setEmail(editTextEmail.getText().toString());
                accountDAO.updateAccount(account);
                accountDAO.sync();
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    public void mathIdElement(){
        accountDAO=new AccountDAO(this);
        account=accountDAO.getAccount();
        editTextFullName = findViewById(R.id.editTextFullName);
        textViewTitle = findViewById(R.id.textViewTitle);
        editTextEmail = findViewById(R.id.editTextEmail);
        btn_dOB = findViewById(R.id.btn_dOB);
        btn_save = findViewById(R.id.btn_save);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        backArrow = findViewById(R.id.backArrow);
        image = findViewById(R.id.image);

        //set value
        editTextEmail.setText(account.getEmail());
        editTextFullName.setText(account.getFullname());
        textViewTitle.setText(account.getUsername());
        if(account.getDateOfBirth()!=null)
        btn_dOB.setText(Tool.DateToStringVN(account.getDateOfBirth()));


        if(account.getGender().equals(Account.GENDER_FEMALE)){
            radioGroupGender.check(R.id.radioButtonFemale);
        } else  if(account.getGender().equals(Account.GENDER_MALE)) radioGroupGender.check(R.id.radioButtonMale);
    }


}