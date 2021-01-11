package com.example.mobile.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.IFragmentCanAddNote;
import com.example.mobile.R;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.NewNoteActivity;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.ui.slideshow.AdapterHomeRecyclerView;
import com.example.mobile.ui.home.HomeViewModel;

public class NoteFragment extends Fragment implements IFragmentCanAddNote {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    View root;
    Package currentPackage;

    AdapterHomeRecyclerView adapterHomeRecyclerView;
    ConnectionDatabaseLocalMobile connectionDatabaseLocalMobile;
    HomeActivity activity;

    public NoteFragment() {
    }




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_notes, container, false);
        init();
        connectionDatabaseLocalMobile= new ConnectionDatabaseLocalMobile(this.getActivity());
        Bundle bundle = this.getArguments();

        if(bundle != null){
            Parcelable p=bundle.getParcelable("currentPackage");
            if(p!=null){
                currentPackage=(Package) p;
                currentPackage.setNotebooks(connectionDatabaseLocalMobile.getNotebooks(currentPackage.getId()));
                Log.e("getListNote", currentPackage.getNotebooks().size()+"");
            }
        }


        if(this.getActivity() instanceof AppCompatActivity){
            this.activity=(HomeActivity) this.getActivity();
        }else{
            Log.e("Error", "Appcompat");
        }


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
    public <T> void startActivity(android.content.Context context, Class<T> classActivity){
        Intent intent = new Intent(context, NewNoteActivity.class);
        intent.putExtra("idPackage",this.currentPackage.getId());
        this.getActivity().startActivityForResult(intent,HomeActivity.NOTE_FRAGMENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void updateApdater(Notebook notebook) {

        this.currentPackage.getNotebooks().add(notebook);

        this.adapterHomeRecyclerView.notifyDataSetChanged();
        Log.e("Update","OK");
    }





}