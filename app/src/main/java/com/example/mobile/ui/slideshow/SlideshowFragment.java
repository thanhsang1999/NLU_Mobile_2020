package com.example.mobile.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.model.Package;
import com.example.mobile.R;

import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private ConnectionDatabaseLocalMobile c;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new
                ViewModelProvider(this.getActivity()).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        final GridView gridView = root.findViewById(R.id.gridView);
        c= new ConnectionDatabaseLocalMobile(this.getActivity());
        List<Package> aPackages = c.getPackages();

        gridView.setAdapter(new PackageItemAdapter(this, aPackages));
        init();
        return root;
    }

    private void init() {
        HomeActivity.fab.hide();
    }
}