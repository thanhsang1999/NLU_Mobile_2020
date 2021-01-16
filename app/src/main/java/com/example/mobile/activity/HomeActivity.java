package com.example.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.mobile.ChangePassActivity;
import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.ExitConfirmDialogFragment;
import com.example.mobile.R;
import com.example.mobile.model.ModelLogin;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    View navFooter1,navFooter2;
    DrawerLayout drawer;
    public NavigationView navigationView;
    Toolbar toolbar;
    public static FloatingActionButton fab;
    NavController navController;
    NavigationView nav_view;
    ImageView profile;
    ActionMode actionMode;
    TextView footer_item_login,footer_item_changePass,footer_item_logout;
    AccessToken accessToken;
    String tenNguoiDung = "";
//    HomeActivity homeActivity = new HomeActivity();
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_home);

        init();

        setSupportActionBar(toolbar);

        AccessToken accessToken = layTenFacebook();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });
        footer_item_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken accessToken = layTenFacebook();
                if(accessToken==null){
                    Intent intent = new Intent(HomeActivity.this, ChangePassActivity.class);
                    startActivity(intent);
                    finish();
                }else{

                }

            }
        });
        footer_item_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken accessToken = layTenFacebook();
                if(accessToken==null){
                    Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                   //Giờ test lại thử, này để trống thôi là ok
                }

            }
        });
        footer_item_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(accessToken!=null){
                    LoginManager.getInstance().logOut();
                    footer_item_logout.setVisibility(View.GONE);
                    footer_item_changePass.setVisibility(View.GONE);
                    footer_item_login.setText("Đăng nhập");// rồi á

                }

            }
        });
//        facebook
        layTenFacebook();

//        sharedPreferences = getApplicationContext().getSharedPreferences("name", Context.MODE_PRIVATE);
//        footer_item_login.setText(sharedPreferences.getString(MY_SHARED_PREFERENCES, ""));



        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        //xử lý nav bottom


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationBottom();



        navigationBackPressed();

        navigationView.setCheckedItem(R.id.nav_home);


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
        profile= findViewById(R.id.profile);
        footer_item_login= findViewById(R.id.footer_item_login);
        footer_item_logout= findViewById(R.id.footer_item_logout);
        footer_item_changePass= findViewById(R.id.footer_item_changePass);
        nav_view= findViewById(R.id.nav_view);

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

        accessToken = layTenFacebook();
        if(accessToken != null) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                tenNguoiDung = object.getString("name");
                                footer_item_login.setText(tenNguoiDung);
                                footer_item_logout.setVisibility(View.VISIBLE);
                                footer_item_changePass.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );

        Bundle parameter = new Bundle();
        parameter.putString("fields","name");
        graphRequest.setParameters(parameter);
        graphRequest.executeAsync();
        }


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
// xử lý lấy tên người dùng facebook
        //lấy tokeen
        public AccessToken layTenFacebook(){
            ModelLogin modelLogin = new ModelLogin();
            AccessToken accessToken = modelLogin.LayTokenFacebook();

            return accessToken;
        }



//end xử lý lấy tên người dùng facebook
}