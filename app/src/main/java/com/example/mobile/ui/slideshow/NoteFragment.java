package com.example.mobile.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.IFragmentShowNote;
import com.example.mobile.R;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.NewNoteActivity;
import com.example.mobile.database.sqlite.NoteDAO;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.ui.home.HomeViewModel;

import java.util.List;

public class NoteFragment extends Fragment implements IFragmentShowNote {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    EditText titlePackage;
    View root;
    Package currentPackage;

    AdapterNoteFragmentRecyclerView adapterHomeRecyclerView;
    NoteDAO connectionDatabaseLocalMobile;
    HomeActivity activity;

    public NoteFragment() {
    }




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_notes, container, false);

        connectionDatabaseLocalMobile= new NoteDAO(this.getActivity());
        Bundle bundle = this.getArguments();

        if(bundle != null){
            Parcelable p=bundle.getParcelable("currentPackage");
            if(p!=null){
                currentPackage=(Package) p;
                currentPackage.setNotebooks(connectionDatabaseLocalMobile.getNotebooks(currentPackage.getId()));
                Log.e("getListNote", currentPackage.getNotebooks().size()+"");
            }
        }
        init();


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
        adapterHomeRecyclerView = new AdapterNoteFragmentRecyclerView(currentPackage,NoteFragment.this);
        recyclerView.setAdapter(adapterHomeRecyclerView);
        return root;
    }

    private void init() {
        HomeActivity.fab.show();

        recyclerView = root.findViewById(R.id.recyclerViewHome);
        titlePackage = root.findViewById(R.id.editTextNamePackage);
        titlePackage.setText(currentPackage.getName());
        titlePackage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("tilepackage", "update");
                    currentPackage.setName(titlePackage.getText().toString());
                    connectionDatabaseLocalMobile.updatePackage(currentPackage, true);
                    connectionDatabaseLocalMobile.sync();

                }
            }
        });
    }
    @Override
    public void startActivityNewNote(android.content.Context context, int idNotebook, int index){
        if(idNotebook==0){
            Intent intent = new Intent(context, NewNoteActivity.class);
            intent.putExtra("idPackage",this.currentPackage.getId());
            intent.putExtra("index",index);
            this.getActivity().startActivityForResult(intent,HomeActivity.NEW_NOTEBOOK);
        }else{
            Intent intent = new Intent(context, NewNoteActivity.class);
            intent.putExtra("idPackage",this.currentPackage.getId());
            intent.putExtra("idNotebook",idNotebook);
            intent.putExtra("index",index);
            this.getActivity().startActivityForResult(intent,HomeActivity.NEW_NOTEBOOK);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void updateApdater(Notebook notebook, int index) {
        if(index==-1){
            this.currentPackage.getNotebooks().add(notebook);

            this.adapterHomeRecyclerView.notifyDataSetChanged();
            Log.e("Add note new","in adapter");

        }else{
            this.currentPackage.getNotebooks().remove(index);
            this.currentPackage.getNotebooks().add(index, notebook);
            this.adapterHomeRecyclerView.notifyDataSetChanged();
        }

    }
    @Override
    public  void startActivityShareNote(){

    };
    @Override
    public void updateApdaterAfterShared(){

    }





}