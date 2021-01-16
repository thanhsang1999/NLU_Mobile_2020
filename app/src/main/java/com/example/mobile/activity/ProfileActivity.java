package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.R;
import com.example.mobile.model.ModelLogin;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    EditText editTextUserName,editTextEmail,editTextGender;
    ImageView image;
    TextView textViewTitle;
    Button btn_dOB,btn_save;
    RadioGroup radioGroupGender;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private ImageView backArrow;
    //facebook
    AccessToken accessToken;
    String tenNguoiDung = "";
    String email = "";
    String dayOfBirth ="";
    String gender = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_profile);
        as();

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
        };backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        profileFacebook();

    }
public void profileFacebook(){
    accessToken = layTenFacebook();
    if(accessToken != null) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            tenNguoiDung = object.getString("name");
                            btn_save.setVisibility(View.INVISIBLE);
                            //Tên người dùng
                            textViewTitle.setText(tenNguoiDung);
                            editTextUserName.setText(tenNguoiDung);
                            editTextUserName.setFocusable(false);
                            editTextUserName.setEnabled(false);

                            //Mail
                            email = object.getString("email");
                            editTextEmail.setText(email);
                            editTextEmail.setFocusable(false);
                            editTextEmail.setEnabled(false);
                            //DateOfBirth
                            dayOfBirth =object.getString("birthday");
                            btn_dOB.setText(dayOfBirth);
                            btn_dOB.setFocusable(false);
                            btn_dOB.setEnabled(false);

                            //Gender
                            gender = object.getString("gender");
                            editTextGender.setText(gender);
                            editTextGender.setFocusable(false);
                            editTextGender.setEnabled(false);
                            Log.d("email" ,  gender);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Bundle parameter = new Bundle();
        parameter.putString("fields","name,email,birthday,gender");
        graphRequest.setParameters(parameter);
        graphRequest.executeAsync();
    }
}

    public void as(){
        editTextUserName = findViewById(R.id.editTextUserName);
        textViewTitle = findViewById(R.id.textViewTitle);
        editTextEmail = findViewById(R.id.editTextEmail);
        btn_dOB = findViewById(R.id.btn_dOB);
        btn_save = findViewById(R.id.btn_save);
//        radioGroupGender = findViewById(R.id.radioGroupGender);
        backArrow = findViewById(R.id.backArrow);
        image = findViewById(R.id.image);
        editTextGender = findViewById(R.id.editTextGender);
    }

    //lấy tokeen
    public AccessToken layTenFacebook(){
        ModelLogin modelLogin = new ModelLogin();
        AccessToken accessToken = modelLogin.LayTokenFacebook();

        return accessToken;

    }
}