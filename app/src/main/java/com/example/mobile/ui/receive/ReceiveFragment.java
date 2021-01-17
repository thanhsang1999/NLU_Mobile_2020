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
import com.example.mobile.R;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.SeeNoteActivity;
import com.example.mobile.database.sqlite.NoteDAO;
import com.example.mobile.model.Notebook;

import java.util.ArrayList;

public class ReceiveFragment extends Fragment {
    RecyclerView recyclerView;
    View root;


    public AdapterReceive adapterReceive;
    NoteDAO noteDAO;


    public ArrayList<Notebook> notebooks;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_receive, container, false);
        //TODO
        initReceive();
        // du lieu ao
        noteDAO = new NoteDAO(this.getActivity());
        notebooks = noteDAO.getNotebooksLast(-1);

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
    }

    public void startActivitySeeNote(Notebook notebook) {
        Intent intent = new Intent(ReceiveFragment.this.getActivity(), SeeNoteActivity.class);
        intent.putExtra("notebook",notebook);
        this.getActivity().startActivity(intent);

    }
}