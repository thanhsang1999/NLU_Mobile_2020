package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile.Config;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.LogInActivity;
import com.example.mobile.model.Account;
import com.example.mobile.model.MySync;
import com.example.mobile.model.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccountDAO extends ConnectionDatabaseLocalMobile {
    public AccountDAO(Activity activity) {
        super(activity);
    }


    public boolean insert_account(Account account) {

        prepare();
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("fullname", account.getFullname());
        values.put("email", account.getEmail());
        values.put("password", account.getPassword());
        values.put("gender", account.getGender());
        values.put("dateofbirth", Tool.DateToString(account.getDateOfBirth()));
        values.put("id_outside", account.getIdOutSide());
        values.put("outside", account.getOutside());
        Log.e("Account", account.getUsername());
        return this.sqLiteDatabase.insert("tblaccounts", null, values) != -1;
    }
    public boolean updateAccount(Account account){
        ContentValues values = new ContentValues();
        values.put("fullname", account.getFullname());
        values.put("email", account.getEmail());
        values.put("gender", account.getGender());
        values.put("dateofbirth",Tool.DateToString( account.getDateOfBirth()));
        int ret = this.sqLiteDatabase.update("tblaccounts", values, "username=?", new String[]{account.getUsername()+""});
        if(ret==1){
            Log.e("update Account", "Ok");


            MySync sync=new MySync();
            sync.setAction("update");
            sync.setTableName("tblaccount");
            sync.setTime(Tool.DateToString(new Date()));
            if(insert_sync(sync)){
                Log.e("insert","ua sync");
                return true;
            }

        }
        return false;
    }

}
