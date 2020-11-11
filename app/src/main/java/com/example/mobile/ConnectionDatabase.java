package com.example.mobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ConnectionDatabase {
    private Activity activity;
    private SQLiteDatabase sqLiteDatabase;
    public ConnectionDatabase(Activity activity) {
        this.activity=activity;


    }
    private void prepare(){
        if(sqLiteDatabase==null || !sqLiteDatabase.isOpen()){
            sqLiteDatabase=activity.openOrCreateDatabase(R.string.app_name+".db", Context.MODE_PRIVATE, null);
        }
        prepareData();

    }
    public SQLiteDatabase getSqLiteDatabase(){
        prepare();
        return this.sqLiteDatabase;
    }
    private void prepareData(){


        String sql="CREATE TABLE IF NOT EXISTS  tblaccounts(\n" +
                "\t\n" +
                "\tusername TEXT,\n" +
                "\tfullname TEXT,\n" +
                "\temail TEXT PRIMARY KEY,\n" +
                "\tpassword TEXT\n" +
                "\t\n" +
                ");";
        String sql2="CREATE TRIGGER IF NOT EXISTS trigger_username BEFORE INSERT ON tblaccounts \n" +
                "\t when (EXISTS (SELECT * FROM tblaccounts where new.username = username ))\n" +
                "\t\tbegin\t\t\n" +
                "\t\t\t\tSELECT RAISE(ABORT, \"Not insert, username is exists\");\n" +
                "\t\tend";

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);


    }
    public boolean login(String username, String password){
        prepare();
        String columnName[]={"username","password"};

        Cursor cursor= this.sqLiteDatabase.query("tblaccounts",
                columnName,"username = ? and password =? ", new String[]{username, password}, null, null, null
                );
        if(cursor!=null){
            cursor.moveToFirst();

        }
        Log.e("Error", cursor.getCount()+"");
        return cursor.getCount()==1;
    }
    public boolean insert_accounts(Account account){

        prepare();
        ContentValues values = new ContentValues();
        values.put("username",account.getUsername());
        values.put("fullname", account.getFullname());
        values.put("email",account.getEmail());
        values.put("password", account.getPassword());
        return  this.sqLiteDatabase.insert("tblaccounts",null, values)!=-1;
    }
}
