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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountDAO extends ConnectionDatabaseLocalMobile {
    public AccountDAO(Activity activity) {
        super(activity);
    }

    public Account getAccount() {
        prepare();
        String columnName[] = {"username", "fullname", "email", "password"};

        Cursor cursor = this.sqLiteDatabase.query("tblaccounts",
                columnName, null, null, null, null, null
        );

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                if (cursor.getCount() == 1)
                    try {

                        Account account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                        Log.e("Account", account.getUsername());
                        return account;
                    } catch (Exception e) {
                        Log.e("Exception", e.getMessage().toString());
                    }


            }

        }


        return null;
    }
    public boolean insert_account(Account account) {

        prepare();
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("fullname", account.getFullname());
        values.put("email", account.getEmail());
        values.put("password", account.getPassword());
        Log.e("Account", account.getUsername());
        return this.sqLiteDatabase.insert("tblaccounts", null, values) != -1;
    }

}
