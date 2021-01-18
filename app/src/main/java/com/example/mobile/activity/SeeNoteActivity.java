package com.example.mobile.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.R;
import com.example.mobile.adapter.NewNoteAdapter;
import com.example.mobile.model.NoteShared;

import java.util.ArrayList;
import java.util.List;

public class SeeNoteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NewNoteAdapter newNoteAdapter;
    EditText editTextAuthor,editTextTitle,editTextContent;
    NoteShared notebook;
    List<Bitmap> lstBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        notebook = getIntent().getExtras().getParcelable("notebook");
        lstBitmap.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.cat1));
        lstBitmap.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.cat2));
        lstBitmap.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.cat3));
        init();
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(SeeNoteActivity.this,DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(SeeNoteActivity.this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SeeNoteActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        for(byte[] b:notebook.getImages()){
//            lstBitmap.add(Tool.getBitmapFromByte(b));
//        }

        newNoteAdapter = new NewNoteAdapter(SeeNoteActivity.this,lstBitmap);
        recyclerView.setAdapter(newNoteAdapter);
    }

    private void init() {
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        recyclerView = findViewById(R.id.recyclerViewImage);

        editTextAuthor.setText("Chủ sở hữu: Sang");
        editTextAuthor.setFocusable(false);
        editTextTitle.setText(notebook.getTitle());
        editTextTitle.setFocusable(false);
        editTextContent.setText(notebook.getContent());
        editTextContent.setFocusable(false);
        lstBitmap = new ArrayList<>();

        if (lstBitmap.size()!=0){
            editTextContent.setMinimumHeight(1180);
        }else {
            editTextContent.setMinimumHeight(1780);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:this.finish();
            return true;
            default:return false;
        }
    }
}