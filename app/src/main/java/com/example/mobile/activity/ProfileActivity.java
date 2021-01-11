package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mobile.R;

public class ProfileActivity extends AppCompatActivity {
    RadioButton radioButtonFemale;
    RadioButton radioButtonMale;
    RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
    }
    private void initView(){
        radioButtonMale=findViewById(R.id.radioButtonMale);
        //radioButtonMale.setId(View.generateViewId());
        radioButtonFemale=findViewById(R.id.radioButtonFemale);
       // radioButtonFemale.setId(View.generateViewId());
        RadioButton[] radioButtons=new RadioButton[]{radioButtonMale,radioButtonFemale};


    }
}