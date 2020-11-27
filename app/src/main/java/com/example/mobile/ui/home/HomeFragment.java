package com.example.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;


import com.example.mobile.HomeActivity;
import com.example.mobile.NewNoteActivity;
import com.example.mobile.R;
import com.example.mobile.model.DBConnSQLite;
import com.example.mobile.model.Notebook;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listViewHome;
    View root;
    ArrayList<Notebook> notebooks;
    AdapterHome adapterHome;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        //TODO
        listViewHome.setAdapter(adapterHome);


        return root;
    }

    private void init() {
        HomeActivity.fab.show();
        listViewHome = root.findViewById(R.id.llistViewHome);
        notebooks = HomeActivity.sqLite.GetNotebooks();
        adapterHome = new AdapterHome(root.getContext(),R.layout.list_view_home,notebooks);
    }
}