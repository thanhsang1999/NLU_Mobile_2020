package com.example.mobile;

import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {
    EditText editTextSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

    }

    private void init() {
//        editTextSearch = findViewById(R.id.editTextSearch);
    }
}