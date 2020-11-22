package com.example.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;


import com.example.mobile.HomeItemAdapter;
import com.example.mobile.Note;
import com.example.mobile.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new
                ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final GridView gridView = root.findViewById(R.id.gridView);
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "One", "....................", new Date(), new Date()));
        notes.add(new Note(2, "Two", "....................", new Date(), new Date()));


        gridView.setAdapter(new HomeItemAdapter(this, notes));


//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}