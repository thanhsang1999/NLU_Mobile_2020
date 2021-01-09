package com.example.mobile.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.*;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.R;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;


import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    View root;
    public static Package currentPackage;

    public  static AdapterHomeRecyclerView adapterHomeRecyclerView;
    ConnectionDatabaseLocalMobile connectionDatabaseLocalMobile;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        connectionDatabaseLocalMobile= new ConnectionDatabaseLocalMobile(this.getActivity());
        Bundle bundle = this.getArguments();

        if(bundle != null){
            Parcelable p=bundle.getParcelable("currentPackage");
            if(p!=null){
                currentPackage=(Package) p;
                currentPackage.setNotebooks(connectionDatabaseLocalMobile.GetNotebooks(currentPackage.getId()));
            }
        }
        if(currentPackage==null)
        currentPackage= connectionDatabaseLocalMobile.getLastPackage();

        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(root.getContext(),R.drawable.custom_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterHomeRecyclerView = new AdapterHomeRecyclerView(currentPackage,root.getContext());
        recyclerView.setAdapter(adapterHomeRecyclerView);
        return root;
    }

    private void init() {
        HomeActivity.fab.show();

        recyclerView = root.findViewById(R.id.recyclerViewHome);
    }

}