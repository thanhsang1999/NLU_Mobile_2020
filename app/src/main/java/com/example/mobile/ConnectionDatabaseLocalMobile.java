package com.example.mobile;

import android.app.Activity;
import android.content.ContentValues;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mobile.model.Account;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectionDatabaseLocalMobile extends SQLiteOpenHelper {
    private Activity activity;
    private SQLiteDatabase sqLiteDatabase;



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


        String sql = "CREATE TABLE IF NOT EXISTS  tblaccounts(" +
                "id INTEGER   PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "fullname TEXT," +
                "email TEXT," +
                "password TEXT" +
                ");";
        String sql2 = "CREATE TRIGGER IF NOT EXISTS trigger_username BEFORE INSERT ON tblaccounts \n" +
                "\t when (EXISTS (SELECT * FROM tblaccounts where new.username = username ) OR EXISTS (SELECT * FROM tblaccounts where new.email = email ))\n" +
                "\t\tbegin\t\t\n" +
                "\t\t\t\tSELECT RAISE(ABORT, \"Not insert, username or email is exists\");\n" +
                "\t\tend";
        String sql3 = "CREATE TABLE IF NOT EXISTS  tblpackage(\n" +
                "\tid INTEGER   PRIMARY KEY AUTOINCREMENT,\n" +

                "\tcolor TEXT,\n" +
                "\ttitle TEXT,\n" +

                "\tlast_edit TEXT\n" +
                ");\n";

        String sql4 = "CREATE TABLE IF NOT EXISTS notebook ("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT," +
                "content TEXT,"+
                "id_package integer DEFAULT 1,"+

                "last_edit TEXT);";


        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);



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

    public boolean insert_package(Package p) {

        prepare();
        ContentValues values = new ContentValues();
        values.put("title", p.getName());
        values.put("color", p.getColor());
        values.put("last_edit", p.getLastEdit().getText());

        boolean rs = this.sqLiteDatabase.insert("tblpackage", null, values) != -1;
        p.setId(1);
        Log.e("Package", p.getColor());
        Log.e("Insert Package", "" + rs);
        new Thread(()->{
            ConnectionWebService connectionWebService= new ConnectionWebService(this.activity);
            connectionWebService.insert_package(p, getAccount());
        }).start();
        return rs;
    }

    public List<Package> getPackages() {

        List<Package> aPackages = new ArrayList<>();
        String columnName[] = {"id", "title", "color", "last_edit"};

//        Cursor cursor = this.sqLiteDatabase.query("tblpackage",
//                columnName, null, null, null, null, null
//        );


        Cursor cursor = this.sqLiteDatabase.query("tblpackage",
                columnName, null, null, null, null, null
        );

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    try {

                        Package p = new Package();
                        p.setId(cursor.getInt(0));
                        p.setName(cursor.getString(1));
                        p.setColor(cursor.getString(2));
                        p.setLastEdit(new DateStringConverter(cursor.getString(3)));
                        aPackages.add(p);


                    } catch (Exception e) {
                        Log.e("Error Get Package", e.getMessage());
                    }
                    cursor.moveToNext();
                }
            }


        }


        return aPackages;
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
    public ArrayList<Notebook> GetNotebooks(int idPackage) {
        String query = "SELECT id,title, content,last_edit FROM notebook where id_package="+idPackage;
        ArrayList<Notebook> notebooks = new ArrayList<>();
        Cursor cursor = GetData(query);
        while (cursor.moveToNext()){
            Notebook notebook = new Notebook();
            notebook.setId(cursor.getInt(0));
            notebook.setTitle(cursor.getString(1));
            notebook.setContent(cursor.getString(2));
            notebook.setDateEdit(Tool.StringToDate(cursor.getString(3)));
            notebooks.add(notebook);
        }
        return notebooks;
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

    public Package getLastPackage() {
        String columnName[] = {"id", "title", "color", "last_edit"};
        Cursor cursor = this.sqLiteDatabase.query("tblpackage",
                columnName, null, null,
                null, null, "last_edit desc");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Package p = new Package();
                try {


                    p.setId(cursor.getInt(0));
                    p.setName(cursor.getString(1));
                    p.setColor(cursor.getString(2));
                    String ls=cursor.getString(3);
                    p.setLastEdit(new DateStringConverter(ls));
                    p.setNotebooks(GetNotebooks(p.getId()));


                } catch (Exception e) {
                    Log.e("Get Last Package", e.getMessage());
                }

                return p;

            }


        }
        Package p = new Package();

        p.setName("Default");
        p.setColor("color_blue");
        p.setLastEdit(new DateStringConverter());
        p.setNotebooks(new ArrayList<Notebook>());
        insert_package(p);
        return p;

    }

}
