package com.example.mobile.ui.receive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobile.R;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.SeeNoteActivity;
import com.example.mobile.database.sqlite.NoteDAO;
import com.example.mobile.database.sqlite.NoteSharedDAO;
import com.example.mobile.database.webservice.ConnectionWebService;
import com.example.mobile.model.NoteShared;
import com.example.mobile.model.Notebook;

import java.util.ArrayList;

public class ReceiveFragment extends Fragment {
    RecyclerView recyclerView;
    View root;


    public AdapterReceive adapterReceive;
    NoteSharedDAO noteSharedDAO;
    SwipeRefreshLayout swiperefresh;


    public ArrayList<NoteShared> notebooks;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_receive, container, false);
        Log.e("Run fragment","Receive");
        initReceive();
        // du lieu ao
        noteSharedDAO = new NoteSharedDAO(this.getActivity());
        notebooks = noteSharedDAO.getAccessNoteSharedLast(-1);

        //set thuoc tinh RecyclerView
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(root.getContext(),R.drawable.custom_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e("HomeFragment","Run");
        adapterReceive = new AdapterReceive(ReceiveFragment.this, notebooks);
        recyclerView.setAdapter(adapterReceive);

        return root;
    }

    private void initReceive() {
        recyclerView = root.findViewById(R.id.recyclerViewReceive);
        HomeActivity.fab.hide();
        swiperefresh=root.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notebooks.clear();
                ConnectionWebService connectionWebService= new ConnectionWebService(ReceiveFragment.this.getActivity());
                connectionWebService.takeDataMyNoteShared();
                connectionWebService.takeDateAccessNoteShared();
                notebooks.addAll(noteSharedDAO.getAccessNoteSharedLast(-1));
                adapterReceive.notifyDataSetChanged();
                swiperefresh.setRefreshing(false);
            }
        });
    }

    public void startActivitySeeNote(NoteShared notebook) {
        Intent intent = new Intent(ReceiveFragment.this.getActivity(), SeeNoteActivity.class);
        intent.putExtra("notebook",notebook);
        this.getActivity().startActivity(intent);

    }
}