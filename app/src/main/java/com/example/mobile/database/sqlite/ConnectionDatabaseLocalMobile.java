package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mobile.R;
import com.example.mobile.model.Account;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectionDatabaseLocalMobile extends SQLiteOpenHelper {
    protected Activity activity;
    protected SQLiteDatabase sqLiteDatabase;



    public ConnectionDatabaseLocalMobile(Activity activity) {
        super(activity.getBaseContext(), R.string.app_name+".db", null, 1);
        this.activity = activity;
        prepare();

    }

    public void prepare() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = activity.openOrCreateDatabase(R.string.app_name + ".db", activity.MODE_PRIVATE, null);
        }
        prepareData();

    }

    public SQLiteDatabase getSqLiteDatabase() {
        prepare();
        return this.sqLiteDatabase;
    }

    private void prepareData() {


        String sqlCreateTableAccount = "CREATE TABLE IF NOT EXISTS  tblaccounts(" +
                "id INTEGER   PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "fullname TEXT," +
                "email TEXT," +
                "password TEXT" +
                ");";
        sqLiteDatabase.execSQL(sqlCreateTableAccount);
        String sqlCreateTriggerInsertAccount = "CREATE TRIGGER IF NOT EXISTS trigger_username BEFORE INSERT ON tblaccounts \n" +
                "\t when (EXISTS (SELECT * FROM tblaccounts where new.username = username ) OR EXISTS (SELECT * FROM tblaccounts where new.email = email ))\n" +
                "\t\tbegin\t\t\n" +
                "\t\t\t\tSELECT RAISE(ABORT, \"Not insert, username or email is exists\");\n" +
                "\t\tend";

        sqLiteDatabase.execSQL(sqlCreateTriggerInsertAccount);
        String sqlCreateTablePackage = "CREATE TABLE IF NOT EXISTS  tblpackage(\n" +
                "\tid INTEGER   PRIMARY KEY AUTOINCREMENT,\n" +

                "\tcolor TEXT,\n" +
                "\ttitle TEXT,\n" +

                "\tlast_edit TEXT\n" +
                ");\n";

        sqLiteDatabase.execSQL(sqlCreateTablePackage);

        String sqlCreateTableNotebook = "CREATE TABLE IF NOT EXISTS notebook ("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT," +
                "content TEXT,"+
                "id_package integer,"+
                "remind TEXT,"+
                "last_edit TEXT);";
        sqLiteDatabase.execSQL(sqlCreateTableNotebook);

        String sqlCreateTableImage = "CREATE TABLE IF NOT EXISTS images_note ("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "id_notebook integer," +
                "image blob,"+
                "last_edit TEXT);";
        sqLiteDatabase.execSQL(sqlCreateTableImage);



    }

    public void earse() {

        String sql = "DELETE FROM tblaccounts;";
        String sql2 = "DELETE FROM tblpackage";
        String sql3 = "DELETE FROM notebook";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        prepare();


    }












    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return  database.rawQuery(sql,null);
    }
    public String getColorPackage(int idPackage){
        String query = "SELECT color FROM tblpackage WHERE id='"+idPackage+"'";
        Cursor cursor = GetData(query);
        cursor.moveToNext();
        return cursor.getString(0);
    }



    /**
     * true => has data
     * false => not data
     * */
    public boolean checkDBExists(String sql) {
        Cursor cursor = GetData(sql);
        return cursor.moveToNext();
    }


    public boolean CreateDefaultPackage(Date dateEdit){
        if (!checkDBExists("SELECT title FROM tblpackage WHERE id='1'")){
            String sql = "INSERT INTO tblpackage(id,color,title,last_edit) VALUES (1,'color_blue','Default','"+Tool.DateToString(dateEdit)+"')";
            QueryData(sql);
            return true;
        }else {
            return false;
        }
    }








}
