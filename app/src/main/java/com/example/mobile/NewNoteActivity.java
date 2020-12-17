package com.example.mobile;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.mobile.model.DBConnSQLite;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.ui.home.HomeFragment;

public class NewNoteActivity extends AppCompatActivity {
    EditText editTextTitle,editTextContent;
    ConstraintLayout Mainlayout,contentLayout;
    DBConnSQLite sqLite;
    ConnectionWebService connectionWebService;
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
    }

    private void init() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        Mainlayout = findViewById(R.id.mainLayout);
        contentLayout = findViewById(R.id.contentLayout);
        editTextContent.requestFocus();
        sqLite  = new DBConnSQLite(this);
        connectionWebService = new ConnectionWebService(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                String title = editTextTitle.getText().toString();
                String textContent = editTextContent.getText().toString();
                int idPackage = 1;
                String dateEdit = new DateStringConverter().getText();
                if (!textContent.equals("")){
                   sqLite.QueryData("INSERT INTO notebook VALUES (null,'"+title+"','"+textContent+"','"+idPackage+"','"+dateEdit+"');");
                    HomeFragment.notebooks.add(HomeActivity.sqLite.GetLastNotebooks());
                    HomeFragment.adapterHomeRecyclerView.notifyDataSetChanged();
                }
//                connectionWebService.QuerySQL();
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