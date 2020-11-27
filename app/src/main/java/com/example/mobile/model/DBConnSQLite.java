package com.example.mobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBConnSQLite extends SQLiteOpenHelper {

    public DBConnSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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
            notebook.setDateCreate(Tool.StringToDate(cursor.getString(4)));
            notebook.setDateEdit(Tool.StringToDate(cursor.getString(5)));
            notebooks.add(notebook);
        }
        return notebooks;
    }
}
