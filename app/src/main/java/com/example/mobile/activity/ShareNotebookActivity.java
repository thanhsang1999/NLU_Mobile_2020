package com.example.mobile.activity;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.R;
import com.example.mobile.adapter.ShareNoteAdapter;
import com.example.mobile.model.InfoShare;

import java.util.ArrayList;
import java.util.List;

public class ShareNotebookActivity extends AppCompatActivity {
    RecyclerView recyclerViewShare;
    EditText editTextShare;
    List<InfoShare> infoShares;
    ShareNoteAdapter shareNoteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_notebook);

        Toolbar toolbar = findViewById(R.id.toolbarShare);
        toolbar.setTitle("Chia sẻ notebook");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        infoShares = new ArrayList<>();
        infoShares.add(new InfoShare("sang","anonkill1999@gmail.com"));
        infoShares.add(new InfoShare("sang1","anonkill19991@gmail.com"));
        infoShares.add(new InfoShare("sang2","anonkill19992@gmail.com"));
        infoShares.add(new InfoShare("sang3","anonkill19994@gmail.com"));
        infoShares.add(new InfoShare("sang3","anonkill19994@gmail.com"));
        infoShares.add(new InfoShare("sang3","anonkill19994@gmail.com"));
        infoShares.add(new InfoShare("sang3","anonkill19994@gmail.com"));

        recyclerViewShare = findViewById(R.id.recyclerViewShare);

        recyclerViewShare.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ShareNotebookActivity.this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(ShareNotebookActivity.this, R.drawable.custom_divider));
        recyclerViewShare.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShareNotebookActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerViewShare.setLayoutManager(linearLayoutManager);

        shareNoteAdapter = new ShareNoteAdapter(this,infoShares);
        recyclerViewShare.setAdapter(shareNoteAdapter);
        // edittext
        editTextShare = findViewById(R.id.editTextInputShare);
        // set action edittext
        editTextShare.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //TODO
                infoShares.add(new InfoShare("demo","anonkill19994@gmail.com"));
                shareNoteAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return false;
        }
    }

}