package com.example.mobile;

import android.app.Activity;
import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mobile.model.Account;
import com.example.mobile.model.Package;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectionDatabaseLocalMobile {
    private Activity activity;
    private SQLiteDatabase sqLiteDatabase;

    public ConnectionDatabaseLocalMobile(Activity activity) {
        this.activity = activity;


    }

    private void prepare() {
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
                "\tcreate_date TEXT,\n" +
                "\tlast_edit TEXT\n" +
                ");\n";

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);


    }

    public void earse() {
        prepare();
        String sql = "DELETE  FROM tblaccounts;";
        sqLiteDatabase.execSQL(sql);


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

                        Account account = new Account(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

                        Log.e("Account", account.getUsername());
                        return account;
                    } catch (Exception e) {

                    }


            }

        }


        return null;
    }

    public boolean insert_accounts(Account account) {

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
        values.put("create_date", p.getDateCreate().getText());
        boolean rs = this.sqLiteDatabase.insert("tblpackage", null, values) != -1;
        Log.e("Package", p.getColor());
        Log.e("Insert Package", "" + rs);
        return rs;
    }

    public List<Package> getPackages() {
        prepare();
        List<Package> aPackages = new ArrayList<>();
        String columnName[] = {"id", "title", "color", "create_date", "last_edit"};

//        Cursor cursor = this.sqLiteDatabase.query("tblpackage",
//                columnName, null, null, null, null, null
//        );
        Cursor cursor = this.sqLiteDatabase.rawQuery("select * from tblpackage",null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    try {

                        Package p = new Package(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("title")),
                                cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("create_date")), cursor.getString(cursor.getColumnIndex("last_edit")));
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
}
