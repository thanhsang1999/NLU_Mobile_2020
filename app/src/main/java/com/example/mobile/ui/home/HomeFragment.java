package com.example.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;


import com.example.mobile.HomeActivity;
import com.example.mobile.R;
import com.example.mobile.model.Notebook;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listViewHome;
    View root;
    public static List<Notebook> notebooks;
    public static AdapterHome adapterHome;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        //TODO
        adapterHome = new AdapterHome(root.getContext(),R.layout.list_view_home,notebooks);
        notebooks.addAll(HomeActivity.sqLite.GetNotebooks());
        listViewHome.setAdapter(adapterHome);
        adapterHome.notifyDataSetChanged();
        return root;
    }

    private void init() {
        HomeActivity.fab.show();
        notebooks = new ArrayList<>();
        listViewHome = root.findViewById(R.id.listViewHome);
    }
}