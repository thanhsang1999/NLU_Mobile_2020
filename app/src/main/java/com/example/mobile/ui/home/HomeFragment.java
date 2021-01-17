package com.example.mobile.ui.home;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobile.IFragmentShowNote;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.R;
import com.example.mobile.activity.NewNoteActivity;
import com.example.mobile.activity.ShareNotebookActivity;
import com.example.mobile.database.sqlite.NoteDAO;
import com.example.mobile.model.Notebook;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class HomeFragment extends Fragment implements IFragmentShowNote {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    View root;


    public  AdapterHomeRecyclerView adapterHomeRecyclerView;
    NoteDAO connectionDatabaseLocalMobile;
    HomeActivity activity;


    SwipeRefreshLayout swiperefresh;

    public ArrayList<Notebook> listNotebook;






    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        connectionDatabaseLocalMobile= new NoteDAO(this.getActivity());




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
        //Log.e("HomeFragment","Run");
        adapterHomeRecyclerView = new AdapterHomeRecyclerView(listNotebook,HomeFragment.this);
        recyclerView.setAdapter(adapterHomeRecyclerView);
        return root;
    }

    private void init() {
        HomeActivity.fab.show();

        recyclerView = root.findViewById(R.id.recyclerViewHome);
        swiperefresh=root.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listNotebook.clear();
                listNotebook.addAll(connectionDatabaseLocalMobile.getNotebooksLast(-1));
                adapterHomeRecyclerView.notifyDataSetChanged();
                swiperefresh.setRefreshing(false);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    listNotebook.clear();
                    listNotebook.addAll(connectionDatabaseLocalMobile.getNotebooksLast(-1));
                    HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterHomeRecyclerView.notifyDataSetChanged();
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public  void startActivityNewNote(android.content.Context context, int idNotebook, int index) {
        Log.e("index",""+index);
        if(idNotebook==0){
            Intent intent = new Intent(context, NewNoteActivity.class);

            intent.putExtra("idPackage",0);
            intent.putExtra("index",-1);


            this.getActivity().startActivityForResult(intent,HomeActivity.NEW_NOTEBOOK);
        }else{
            Intent intent = new Intent(context, NewNoteActivity.class);

            intent.putExtra("idPackage",0);
            intent.putExtra("idNotebook",idNotebook);
            intent.putExtra("index",index);

            this.getActivity().startActivityForResult(intent,HomeActivity.NEW_NOTEBOOK);
        }

    }
    @Override
    public void updateApdater(Notebook notebook, int index) {
        Log.e("update",index+"");
        if(index==-1){
            this.listNotebook.add(0,notebook);
            this.adapterHomeRecyclerView.notifyDataSetChanged();
            Log.e("Add note new","in adapter");
        }else{
            this.listNotebook.remove(index);
            this.listNotebook.add(index, notebook);

            this.adapterHomeRecyclerView.notifyDataSetChanged();
        }

        Log.e("imageCount",notebook.getImages().size()+"");


    }
    @Override
    public  void startActivityShareNote(){
        if(this.getActivity() instanceof  HomeActivity){
            Log.e("Start activity","shared note");
            Intent intent = new Intent(this.getActivity(), ShareNotebookActivity.class);
            ArrayList<Integer> lstShared= new ArrayList<>();
            for (Notebook n:listNotebook) {
                if(n.getChecked()==true)
                lstShared.add(n.getId());

            }
            intent.putIntegerArrayListExtra("lstShared",lstShared);
            ((HomeActivity)this.getActivity()).startActivityForResult(intent, HomeActivity.SHARE_NOTEBOOK);
        }


    };
    @Override
    public void updateApdaterAfterShared(){
        Log.e("Run", "updateApdaterAfterShared");
        for (int i=listNotebook.size()-1;i>=0;i--) {
            if(listNotebook.get(i).getChecked()==true)
               listNotebook.remove(i);

        }
        adapterHomeRecyclerView.notifyDataSetChanged();
    }




}