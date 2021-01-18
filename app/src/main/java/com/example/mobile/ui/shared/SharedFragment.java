package com.example.mobile.ui.shared;

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
import com.example.mobile.activity.EditNoteActivity;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.database.sqlite.NoteSharedDAO;
import com.example.mobile.model.NoteShared;

import java.util.ArrayList;

public class SharedFragment extends Fragment {
    RecyclerView recyclerView;
    View root;
    public AdapterShared adapterShared;
    NoteSharedDAO noteSharedDAO;
    SwipeRefreshLayout swiperefresh;
    public ArrayList<NoteShared> notebooks;
    public int CODE_SHARED_FRAGMENT = 1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_shared, container, false);
        //TODO
        initReceive();
        // du lieu ao
        noteSharedDAO = new NoteSharedDAO(this.getActivity());
        notebooks = noteSharedDAO.getNoteSharedLast(-1);

        //set thuoc tinh RecyclerView
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(root.getContext(),R.drawable.custom_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e("HomeFragment","Run");
        adapterShared = new AdapterShared(SharedFragment.this, notebooks);
        recyclerView.setAdapter(adapterShared);

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
                notebooks.addAll(noteSharedDAO.getNoteSharedLast(-1));
                adapterShared.notifyDataSetChanged();
                swiperefresh.setRefreshing(false);
            }
        });
    }

    public void startActivitySeeNote(NoteShared notebook) {
        Intent intent = new Intent(SharedFragment.this.getActivity(), EditNoteActivity.class);
        intent.putExtra("notebook",notebook);
        this.getActivity().startActivityForResult(intent,CODE_SHARED_FRAGMENT);
    }
}