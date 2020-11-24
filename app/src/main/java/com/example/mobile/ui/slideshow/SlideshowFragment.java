package com.example.mobile.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile.model.Package;
import com.example.mobile.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new
                ViewModelProvider(this.getActivity()).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        final GridView gridView = root.findViewById(R.id.gridView);
        List<Package> aPackages = new ArrayList<>();
        aPackages.add(new Package(1, "One", "....................", new Date(), new Date()));
        aPackages.add(new Package(2, "Two", "....................", new Date(), new Date()));


        gridView.setAdapter(new PackageItemAdapter(this, aPackages));

        return root;
    }
}