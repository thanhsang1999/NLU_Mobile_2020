package com.example.mobile.activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.ConnectionWebService;
import com.example.mobile.R;
import com.example.mobile.adapter.NewNoteAdapter;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.model.Notebook;
import com.example.mobile.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;

public class TestNewNoteActivity extends AppCompatActivity {
    EditText editTextTitle,editTextContent;
    ConstraintLayout Mainlayout,contentLayout;
    ConnectionDatabaseLocalMobile sqLite;
    int idPackage;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Notebook notebook;
    FloatingActionButton fabMain,fabCamera,fabFile;
    float translationY = 100f;
    boolean isMenuOpen = false;
    OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    RecyclerView recyclerView;
    NewNoteAdapter newNoteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new_note);
        idPackage=getIntent().getIntExtra("idPackage", 0);
        if(idPackage==0){
            Log.e("Error idp", idPackage+"");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more_menu));
        init();
        FabOnClick();
        //datatest images
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.cat1);
        images.add(R.drawable.cat2);
        images.add(R.drawable.cat3);
        images.add(R.drawable.cat4);

        // RecyclerView
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(TestNewNoteActivity.this,DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(TestNewNoteActivity.this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TestNewNoteActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        newNoteAdapter = new NewNoteAdapter(TestNewNoteActivity.this,images);
        recyclerView.setAdapter(newNoteAdapter);
    }


    private void init() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        Mainlayout = findViewById(R.id.mainLayout);
        contentLayout = findViewById(R.id.contentLayout);
        editTextContent.requestFocus();
        notebook= new Notebook();
        sqLite  = new ConnectionDatabaseLocalMobile(this);
        // set fab
        fabMain =  findViewById(R.id.fabMain);
        fabFile =  findViewById(R.id.fabFile);
        fabCamera =  findViewById(R.id.fabCamera);

        fabFile.setAlpha(0f);
        fabCamera.setAlpha(0f);

        fabFile.setTranslationY(translationY);
        fabCamera.setTranslationY(translationY);
        // RecyclerView
        recyclerView = findViewById(R.id.recyclerViewImage);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                String title = editTextTitle.getText().toString();
                String textContent = editTextContent.getText().toString();

                String dateEdit = new DateStringConverter().getText();
                notebook.setTitle(title);
                notebook.setContent(textContent);
                notebook.setDateEdit(new Date());

                if (!textContent.equals("")){
                    if(idPackage==1)
                        sqLite.CreateDefaultPackage(new Date());

                    sqLite.QueryData("INSERT INTO notebook (id, title, content, id_package, last_edit) VALUES (null,'"+title+"','"+textContent+"','"+idPackage+"','"+dateEdit+"');");
                    HomeFragment.currentPackage.getNotebooks().add(notebook);
                    HomeFragment.adapterHomeRecyclerView.notifyDataSetChanged();
                }

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
//
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(TestNewNoteActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                                c.set(Calendar.MINUTE,minute);
                                                notebook.setRemind(c);
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
    private void OpenMenuFab(){
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(overshootInterpolator).rotation(45f).setDuration(300).start();
        fabFile.animate().translationY(0f).alpha(1f).setInterpolator(overshootInterpolator).setDuration(300);
        fabCamera.animate().translationY(0f).alpha(1f).setInterpolator(overshootInterpolator).setDuration(300);
    }
    private void CloseMenuFab(){
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(overshootInterpolator).rotation(0f).setDuration(300).start();
        fabFile.animate().translationY(translationY).alpha(0f).setInterpolator(overshootInterpolator).setDuration(300);
        fabCamera.animate().translationY(translationY).alpha(0f).setInterpolator(overshootInterpolator).setDuration(300);
    }
    private void FabOnClick() {
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen){
                    CloseMenuFab();
                }else {
                    OpenMenuFab();
                }
            }
        });
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen){
                    CloseMenuFab();
                }else {
                    OpenMenuFab();
                }
            }
        });
        fabFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen){
                    CloseMenuFab();
                }else {
                    OpenMenuFab();
                }
            }
        });
    }
}