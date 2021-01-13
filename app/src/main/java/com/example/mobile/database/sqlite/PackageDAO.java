package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.mobile.ConnectionWebService;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PackageDAO  extends AccountDAO {
    public PackageDAO(Activity activity) {
        super(activity);
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
                        p.setLastEdit(Tool.StringToDate(cursor.getString(3)));
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
    public boolean insert_package(Package p) {

        prepare();
        ContentValues values = new ContentValues();
        values.put("title", p.getName());
        values.put("color", p.getColor());
        values.put("last_edit", Tool.DateToString( p.getLastEdit()));

        boolean rs = this.sqLiteDatabase.insert("tblpackage", null, values) != -1;

        Log.e("Insert Package", "" + rs);
        if(rs){
            final Package p2=getLastPackage();
            new Thread(()->{
                ConnectionWebService connectionWebService= new ConnectionWebService(this.activity);
                connectionWebService.insert_package(p2, getAccount());
            }).start();

        }

        return rs;
    }
    public Package getLastPackage() {
        String columnName[] = {"id", "title", "color", "last_edit"};
        Cursor cursor = this.sqLiteDatabase.query("tblpackage",
                columnName, null, null,
                null, null, "last_edit desc limit 1");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Package p = new Package();
                try {


                    p.setId(cursor.getInt(0));
                    p.setName(cursor.getString(1));
                    p.setColor(cursor.getString(2));
                    String ls=cursor.getString(3);
                    p.setLastEdit(Tool.StringToDate(ls));
                    p.setNotebooks(getNotebooks(p.getId()));


                } catch (Exception e) {
                    Log.e("Get Last Package", e.getMessage());
                }

                return p;

            }


        }
        Package p = new Package();
        p.setId(1);
        p.setName("Default");
        p.setColor("color_blue");
        p.setLastEdit(new Date());
        p.setNotebooks(new ArrayList<Notebook>());
        insert_package(p);
        return p;

    }
    public int updatePackageDateEdit(int idPackage, Date lastEdit){
        ContentValues values = new ContentValues();
        values.put("last_edit",Tool.DateToString(lastEdit));

        int ret = this.sqLiteDatabase.update("tblpackage", values, "id=?", new String[]{idPackage+""});
        return ret;
    }
    public ArrayList<Notebook> getNotebooks(int idPackage) {
        String query = "SELECT id,title, content,last_edit FROM notebook where id_package="+idPackage+" order by last_edit desc";
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
}
