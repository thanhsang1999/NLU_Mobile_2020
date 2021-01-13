package com.example.mobile.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.*;

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
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.R;
import com.example.mobile.activity.NewNoteActivity;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;

import java.util.List;

public class HomeFragment extends Fragment implements IFragmentCanAddNote {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    View root;


    public  AdapterHomeRecyclerView adapterHomeRecyclerView;
    ConnectionDatabaseLocalMobile connectionDatabaseLocalMobile;
    HomeActivity activity;
    List<Notebook> listNotebook;





    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        connectionDatabaseLocalMobile= new ConnectionDatabaseLocalMobile(this.getActivity());




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
        listNotebook= connectionDatabaseLocalMobile.getNotebooksLast(-1);
        adapterHomeRecyclerView = new AdapterHomeRecyclerView(listNotebook,HomeFragment.this);
        recyclerView.setAdapter(adapterHomeRecyclerView);
        return root;
    }

    private void init() {
        HomeActivity.fab.show();

        recyclerView = root.findViewById(R.id.recyclerViewHome);
    }
    public <T> void startActivity(android.content.Context context, Class<T> classActivity, int idNotebook, int index) {
        if(idNotebook==0){
            Intent intent = new Intent(context, NewNoteActivity.class);

            intent.putExtra("idPackage",0);

            this.getActivity().startActivityForResult(intent,HomeActivity.HOME_FRAGMENT);
        }else{
            Intent intent = new Intent(context, NewNoteActivity.class);

            intent.putExtra("idPackage",0);
            intent.putExtra("idNotebook",idNotebook);
            intent.putExtra("index",index);

            this.getActivity().startActivityForResult(intent,HomeActivity.HOME_FRAGMENT);
        }

    }
    @Override
    public void updateApdater(Notebook notebook, int index) {
        if(index==-1){
            this.listNotebook.add(0,notebook);
            this.adapterHomeRecyclerView.notifyDataSetChanged();
            Log.e("Add note new","in adapter");
        }else{
            this.listNotebook.remove(index);
            this.listNotebook.add(index, notebook);

            this.adapterHomeRecyclerView.notifyDataSetChanged();
        }


    }




}