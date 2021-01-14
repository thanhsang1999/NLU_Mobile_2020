package com.example.mobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.mobile.ExitConfirmDialogFragment;
import com.example.mobile.IFragmentCanAddNote;
import com.example.mobile.R;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;
import com.example.mobile.ui.home.AdapterHomeRecyclerView;
import com.example.mobile.ui.home.HomeFragment;
import com.example.mobile.ui.slideshow.PackageItemAdapter;
import com.example.mobile.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    View navFooter1,navFooter2;
    DrawerLayout drawer;
    public NavigationView navigationView;
    Toolbar toolbar;
    public static FloatingActionButton fab;
    public NavController navController;
    ImageView profile;
    public ActionMode actionMode;
    HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        setSupportActionBar(toolbar);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationBottom();



        navigationBackPressed();

        navigationView.setCheckedItem(R.id.nav_home);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment= HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);


                if(fragment instanceof NavHostFragment){

                    fragment=fragment.getChildFragmentManager().getFragments().get(0);
                }

                if(fragment instanceof  IFragmentCanAddNote){

                    IFragmentCanAddNote iFragmentCanAddNote=(IFragmentCanAddNote)fragment;
                    iFragmentCanAddNote.startActivity(HomeActivity.this, NewNoteActivity.class, 0, -1);


                }


            }
        });




    }
    private void navigationBackPressed(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager= getSupportFragmentManager();
                Fragment currentFragment=fragmentManager.findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0);
                if(currentFragment instanceof HomeFragment){
                    // Call finish() on your Activity
                    // Create YesNoDialogFragment
                    DialogFragment dialogFragment = new ExitConfirmDialogFragment();
                    //dialogFragment.getDialog().setCanceledOnTouchOutside(true);
                    // Arguments:
                    Bundle args = new Bundle();
                    args.putString(ExitConfirmDialogFragment.ARG_TITLE, "????");
                    args.putString(ExitConfirmDialogFragment.ARG_MESSAGE, "Do you want?");
                    dialogFragment.setArguments(args);
                    // Show:
                    dialogFragment.show(fragmentManager, "Dialog");
                    //finish();

                } else {
                    navController.popBackStack();
                }

            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("toolbar","click");
                //HomeActivity.this.setCurrentItem(0, true);

            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab);
        navFooter2 = findViewById(R.id.footer_item_2);
        navFooter1 = findViewById(R.id.footer_item_1);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        profile= findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void NavigationBottom() {
        navFooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Toast.makeText(HomeActivity.this, "test 1", Toast.LENGTH_SHORT).show();
            }
        });
        navFooter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do footer action
                drawer.closeDrawers();
                Toast.makeText(HomeActivity.this, "test 2", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void showActionMode(){
        actionMode = startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                LoadDataFragmentHome();
                mode.getMenuInflater().inflate(R.menu.menu_item_select,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_delete:
                        ArrayList<Notebook> notebooks = homeFragment.listNotebook;
//                        Tool.SetAllUnChecked(notebooks);
                        homeFragment.adapterHomeRecyclerView.multiSelect=false;
                        removeNoteAtHomeFragment(notebooks);
                        actionMode.finish();
                        // Hoàng làm database chỗ nãy
                        // TODO
                        return true;
                    case R.id.menu_share:
                        Toast.makeText(HomeActivity.this, "Share", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Tool.SetAllUnChecked(homeFragment.listNotebook);
                homeFragment.adapterHomeRecyclerView.notifyDataSetChanged();
                actionMode = null;
            }
        });
    }
private void LoadDataFragmentHome(){
    // lay fragment tu activity

    Fragment fragment1 = HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);


    if(fragment1 instanceof NavHostFragment){

        fragment1=fragment1.getChildFragmentManager().getFragments().get(0);
    }

    if(fragment1 instanceof  HomeFragment){

        homeFragment =(HomeFragment) fragment1;
    }
}
    private ArrayList<Notebook> removeNoteAtHomeFragment(ArrayList<Notebook> notebooks) {
        for (Notebook item: notebooks) {
            if (item.getChecked()){
                notebooks.remove(item);
            }
        }
        return notebooks;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == NOTE_FRAGMENT || requestCode==HOME_FRAGMENT) {

            if(resultCode == Activity.RESULT_OK){
                Notebook notebook = data.getParcelableExtra("notebook");
                int index = data.getIntExtra("index",-1);
                Fragment currentFragment=null;
                FragmentManager fragmentManager= getSupportFragmentManager();

                currentFragment=fragmentManager.findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0);

                if(notebook!=null){

                    if(currentFragment instanceof  IFragmentCanAddNote){
                        IFragmentCanAddNote iFragmentCanAddNote=(IFragmentCanAddNote) currentFragment;
                        iFragmentCanAddNote.updateApdater(notebook,index);


                    }
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }

        }
    }//onActivityResult
    public Fragment beforeFragment;
    public final static int NOTE_FRAGMENT=2;
    public final static int HOME_FRAGMENT=1;


}