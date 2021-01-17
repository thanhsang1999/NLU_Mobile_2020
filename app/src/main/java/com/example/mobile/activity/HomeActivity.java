package com.example.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.mobile.ChangePassActivity;
import com.example.mobile.ExitConfirmDialogFragment;
import com.example.mobile.IFragmentShowNote;
import com.example.mobile.R;
import com.example.mobile.database.sqlite.NoteDAO;
import com.example.mobile.model.ModelLogin;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;
import com.example.mobile.ui.home.HomeFragment;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public ActionMode actionMode;
    TextView footer_item_login,footer_item_changePass,footer_item_logout;
    AccessToken accessToken;
    String tenNguoiDung = "";
//    HomeActivity homeActivity = new HomeActivity();
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    HomeFragment homeFragment;
    NoteDAO sqlite;

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
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_receive)
                .setDrawerLayout(drawer)
                .build();

        //xử lý nav bottom


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

                if(fragment instanceof IFragmentShowNote){

                    IFragmentShowNote iFragmentCanAddNote=(IFragmentShowNote)fragment;
                    iFragmentCanAddNote.startActivityNewNote(HomeActivity.this, 0, -1);


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
        sqlite = new NoteDAO(this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setTitle("Xóa Notebook");
                        builder.setMessage("Bạn có muốn xóa notebook không ?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // OK
                                ArrayList<Notebook> notebooks = homeFragment.listNotebook;
                                homeFragment.adapterHomeRecyclerView.multiSelect=false;
                                removeNoteAtHomeFragment(notebooks);
                                actionMode.finish();
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                ArrayList<Notebook> notebooks = homeFragment.listNotebook;
//                                Tool.SetAllUnChecked(notebooks);
                                homeFragment.adapterHomeRecyclerView.multiSelect=false;
//                                removeNoteAtHomeFragment(notebooks);
                                actionMode.finish();
                            }
                        });
                        builder.show();

                        return true;
                    case R.id.menu_share:
                        Fragment fragment= HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);


                        if(fragment instanceof NavHostFragment){

                            fragment=fragment.getChildFragmentManager().getFragments().get(0);
                        }

                        if(fragment instanceof IFragmentShowNote){

                            IFragmentShowNote iFragmentCanAddNote=(IFragmentShowNote)fragment;
                            iFragmentCanAddNote.startActivityShareNote();


                        }
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
    private void removeNoteAtHomeFragment(ArrayList<Notebook> notebooks) {
        ArrayList<Notebook> notebooks2 = new ArrayList<>(notebooks);
        for (Notebook item: notebooks2) {
            if (item.getChecked()){
                sqlite.DeleteNotebook(item.getId());
                notebooks.remove(item);
            }
        }
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

                    if(currentFragment instanceof IFragmentShowNote){
                        IFragmentShowNote iFragmentCanAddNote=(IFragmentShowNote) currentFragment;
                        iFragmentCanAddNote.updateApdater(notebook,index);


                    }
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }

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
    //onActivityResult
    public Fragment beforeFragment;
    public final static int NOTE_FRAGMENT=2;
    public final static int HOME_FRAGMENT=1;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {

                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}