package com.example.mobile.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
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
import com.example.mobile.database.sqlite.NoteSharedDAO;
import com.example.mobile.database.webservice.AccountServiceHelper;
import com.example.mobile.model.Account;
import com.example.mobile.model.InfoShare;

import java.util.ArrayList;
import java.util.List;

public class ShareNotebookActivity extends AppCompatActivity {
    RecyclerView recyclerViewShare;
    EditText editTextShare;
    List<InfoShare> infoShares;
    ShareNoteAdapter shareNoteAdapter;
    List<Integer> lstShared;
    NoteSharedDAO noteSharedDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_notebook);
        lstShared=getIntent().getIntegerArrayListExtra("lstShared");
        if(lstShared==null || lstShared.size()==0){
            Log.e("Not receice", "noteshared");
        }
        Toolbar toolbar = findViewById(R.id.toolbarShare);
        toolbar.setTitle("Chia sẻ notebook");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        infoShares = new ArrayList<>();
        noteSharedDAO= new NoteSharedDAO(this);
        Account account=noteSharedDAO.getAccount();
        infoShares.add(new InfoShare(account.getUsername(),account.getEmail()));

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
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    AccountServiceHelper accountServiceHelper= new AccountServiceHelper(ShareNotebookActivity.this);
                    accountServiceHelper.getUsernameAndEmail(editTextShare.getText().toString().trim(),shareNoteAdapter);
                    editTextShare.setText("");
                }


                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(infoShares.size()==0){
                    this.finish();
                } else{
                    NoteSharedDAO noteSharedDAO= new NoteSharedDAO(this);
                    for (int indexNotebook=0;indexNotebook<lstShared.size();indexNotebook++){
                        noteSharedDAO.insertNoteShared(lstShared.get(indexNotebook),true);
                        for (InfoShare i:infoShares) {
                            noteSharedDAO.shared(i, lstShared);
                        }

                    }

                }
                return true;
            default:
                return false;
        }
    }

}