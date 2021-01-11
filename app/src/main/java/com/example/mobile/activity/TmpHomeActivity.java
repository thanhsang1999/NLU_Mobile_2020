package com.example.mobile.activity;

import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mobile.R;

public class TmpHomeActivity extends AppCompatActivity {
    EditText editTextSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometmp);
        init();
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

    }

    private void init() {
//        editTextSearch = findViewById(R.id.editTextSearch);
    }
}