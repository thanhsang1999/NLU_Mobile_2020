package com.example.mobile;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.mobile.model.DBConnSQLite;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.ui.home.HomeFragment;

import java.util.Calendar;

public class NewNoteActivity extends AppCompatActivity {
    EditText editTextTitle,editTextContent;
    ConstraintLayout Mainlayout,contentLayout;
    DBConnSQLite sqLite;
    ConnectionWebService connectionWebService;
    private int mYear, mMonth, mDay, mHour, mMinute;
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
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more_menu));
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
            case R.id.reminder:
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    final Calendar c = Calendar.getInstance();
                                    mHour = c.get(Calendar.HOUR_OF_DAY);
                                    mMinute = c.get(Calendar.MINUTE);

                                    // Launch Time Picker Dialog
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(NewNoteActivity.this,
                                            new TimePickerDialog.OnTimeSetListener() {

                                                @Override
                                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                                      int minute) {

                                                    Toast.makeText(NewNoteActivity.this,dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+ "--"+ hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                                                }
                                            }, mHour, mMinute, false);
                                    timePickerDialog.show();
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

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