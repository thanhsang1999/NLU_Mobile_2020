package com.example.mobile;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {
    EditText editTextTitle,editTextContent;
    ConstraintLayout Mainlayout,contentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        init();

        editTextContent.requestFocus();
//        Cursor cursor = HomeActivity.sqLite.GetData("SELECT * FROM note");
//        while (cursor.moveToNext()){
//
//        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse("22/11/1999");
            Toast.makeText(this, ""+date, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void init() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        Mainlayout = findViewById(R.id.mainLayout);
        contentLayout = findViewById(R.id.contentLayout);
        HomeActivity.sqLite.QueryData("DROP TABLE IF EXISTS note");
        HomeActivity.sqLite.QueryData("CREATE TABLE IF NOT EXISTS notebook (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,title TEXT,content TEXT,package integer DEFAULT 1,date_create TEXT,date_edit TEXT);");
//        HomeActivity.sqLite.QueryData("INSERT INTO note VALUES (null,'hôm nay','Lorem Ipsum is simply','1','26/11/2020','26/11/2020');");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuChangeColor:
                contentLayout.setBackgroundResource(R.drawable.ic_bgblue1);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_note_menu,menu);
        return true;
    }
}