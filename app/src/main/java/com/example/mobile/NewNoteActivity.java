package com.example.mobile;


import android.database.Cursor;
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
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.model.Notebook;
import com.example.mobile.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Calendar;
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
//        Cursor cursor = HomeActivity.sqLite.GetData("SELECT * FROM notebook");
//        ArrayList<Notebook> notebooks = new ArrayList<Notebook>();
//        while (cursor.moveToNext()){
//            Notebook notebook = new Notebook();
//            notebook.setId(cursor.getInt(0));
//            notebook.setTitle(cursor.getString(1));
//            notebook.setContent(cursor.getString(2));
//            notebook.setIdPackage(cursor.getInt(3));
//            notebook.setDateCreate(DateStringConverter.StringToDate(cursor.getString(4)));
//            notebook.setDateEdit(DateStringConverter.StringToDate(cursor.getString(5)));
//            editTextContent.setText(notebook.getTitle()+"\n");
//            notebooks.add(notebook);
//        }

//        DateStringConverter date = new DateStringConverter("1999-06-22 12:20:21");


    }

    private void init() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        Mainlayout = findViewById(R.id.mainLayout);
        contentLayout = findViewById(R.id.contentLayout);
        editTextContent.requestFocus();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                String title = editTextTitle.getText().toString();
                String textContent = editTextContent.getText().toString();
                int idPackage = 1;
                String dateCreate = new DateStringConverter().getText();
                if (!textContent.equals("")){
                    HomeActivity.sqLite.QueryData("INSERT INTO notebook VALUES (null,'"+title+"','"+textContent+"','"+idPackage+"','"+dateCreate+"','"+dateCreate+"');");
//                    HomeFragment.notebooks.add(HomeActivity.sqLite.GetLastNotebooks());
//                    HomeFragment.adapterHome.notifyDataSetChanged();
                }
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