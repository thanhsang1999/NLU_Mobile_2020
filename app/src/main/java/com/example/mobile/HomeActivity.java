package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.example.mobile.model.DBConnSQLite;
import com.example.mobile.model.DateStringConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    View navFooter1,navFooter2;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    public static FloatingActionButton fab;
    NavController navController;
    public static DBConnSQLite sqLite;
    ActionMode actionMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDB();
        init();

        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_fragment, new NewNoteFragment(), null);
//                fragmentTransaction.commit();
                Intent intent = new Intent(HomeActivity.this,NewNoteActivity.class);
                startActivity(intent);
            }
        });

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



    }
    private void navigationBackPressed(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Create YesNoDialogFragment
                DialogFragment dialogFragment = new ExitConfirmDialogFragment();


                // Arguments:
                Bundle args = new Bundle();
                args.putString(ExitConfirmDialogFragment.ARG_TITLE, "????");
                args.putString(ExitConfirmDialogFragment.ARG_MESSAGE, "Do you want?");
                dialogFragment.setArguments(args);

                FragmentManager fragmentManager = HomeActivity.this.getSupportFragmentManager();

                // Show:
                dialogFragment.show(fragmentManager, "Dialog");
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab);
        navFooter2 = findViewById(R.id.footer_item_2);
        navFooter1 = findViewById(R.id.footer_item_1);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    }
    private void initDB() {

        sqLite = new DBConnSQLite(this,R.string.app_name + ".db",null,1);
        sqLite.CreateTableNotebook();
        Date date = new Date();
        sqLite.CreateDefaultPackage(date);
        Log.e("bug","thoi de vay di");
       // HomeActivity.sqLite
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
    public ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
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
                    Toast.makeText(HomeActivity.this, "menu_delete", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.menu_share:
                    Toast.makeText(HomeActivity.this, "menu_share", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionModeCallback = null;
        }
    };
    public void showActionMode(){
        actionMode = startSupportActionMode(actionModeCallback);
    }
}