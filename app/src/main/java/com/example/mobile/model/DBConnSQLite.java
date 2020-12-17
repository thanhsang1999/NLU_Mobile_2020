package com.example.mobile.model;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.R;

import java.util.ArrayList;
import java.util.Date;

public class DBConnSQLite extends SQLiteOpenHelper {

    public DBConnSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBConnSQLite(Context context) {
        super(context, R.string.app_name+".db", null, 1);
//        new ConnectionDatabaseLocalMobile((Activity) context).prepare();
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

        Log.e("aaa", String.valueOf(cursor.getCount()));
        return cursor.getString(0);
    }
    public ArrayList<Notebook> GetNotebooks(){
        String query = "SELECT * FROM notebook";
        ArrayList<Notebook> notebooks = new ArrayList<>();
        Cursor cursor = GetData(query);
        while (cursor.moveToNext()){
            Notebook notebook = new Notebook();
            notebook.setId(cursor.getInt(0));
            notebook.setTitle(cursor.getString(1));
            notebook.setContent(cursor.getString(2));
            notebook.setIdPackage(cursor.getInt(3));
            notebook.setDateEdit(Tool.StringToDate(cursor.getString(4)));
            notebooks.add(notebook);
        }
        return notebooks;
    }
    public Notebook GetLastNotebooks(){
        String query = "SELECT * FROM notebook ORDER BY id DESC LIMIT 1";
        Cursor cursor = GetData(query);
        cursor.moveToNext();
        Notebook notebook = new Notebook();
        notebook.setId(cursor.getInt(0));
        notebook.setTitle(cursor.getString(1));
        notebook.setContent(cursor.getString(2));
        notebook.setIdPackage(cursor.getInt(3));
        notebook.setDateEdit(Tool.StringToDate(cursor.getString(4)));

        return notebook;
    }
    /**
     * true => has data
     * false => not data
     * */
    public boolean checkDBExists(String sql) {
        Cursor cursor = GetData(sql);
        return cursor.moveToNext();
    }
    public void CreateTables(){
        String sql = "CREATE TABLE IF NOT EXISTS notebook (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,title TEXT,content TEXT,package integer DEFAULT 1,date_edit TEXT);";
        QueryData(sql);
        String sql1 = "CREATE TABLE IF NOT EXISTS tblpackage (id INTEGER PRIMARY KEY AUTOINCREMENT,color TEXT,title TEXT,last_edit TEXT);";
        QueryData(sql1);
    }
    public void CreateTableNotebook(){
        String sql = "CREATE TABLE IF NOT EXISTS notebook (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,title TEXT,content TEXT,package integer DEFAULT 1,date_edit TEXT);";
        QueryData(sql);
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
