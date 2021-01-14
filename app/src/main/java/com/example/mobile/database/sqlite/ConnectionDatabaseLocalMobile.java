package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.mobile.Config;
import com.example.mobile.R;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.LogoActivity;
import com.example.mobile.activity.WellComeActivity;
import com.example.mobile.model.Account;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.model.MySync;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;
import com.example.mobile.webservice.ultils.MyWorker;
import com.example.mobile.webservice.ultils.PrepareConnectionWebService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

        String sqlCreateTableSync = "CREATE TABLE IF NOT EXISTS tblsync ("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "id_row integer," +
                "action_sync TEXT,"+
                "name_table TEXT,"+
                "time TEXT);";
        sqLiteDatabase.execSQL(sqlCreateTableSync);



    }

    public void earse() {

        String sql = "DELETE FROM tblaccounts;";
        String sql2 = "DELETE FROM tblpackage";
        String sql3 = "DELETE FROM notebook";
        String sql4 = "DELETE FROM tblsync";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
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



    public boolean insert_sync(MySync sync) {


        ContentValues values = new ContentValues();
        values.put("id_row", sync.getIdRow());
        values.put("name_table", sync.getTableName());
        values.put("time", sync.getTime());
        values.put("action_sync", sync.getAction());
        boolean rs = this.sqLiteDatabase.insert("tblsync", null, values) == 1;
        if(rs){
            Log.e("Insert Sync", "Ok" );
        }
        return rs;
    }
    public boolean deleteSync(MySync mySync){
        String table = "tblsync";
        String whereClause = "id=?";
        String[] whereArgs = new String[] { String.valueOf(mySync.getId()) };
        int rs=this.sqLiteDatabase.delete(table, whereClause, whereArgs);
        return rs==1;
    }
    public List<MySync> getAllSync(){
        List<MySync> syncs = new ArrayList<>();
        String columnName[] = {"id", "id_row", "name_table", "time","action_sync"};

        Cursor cursor = this.sqLiteDatabase.query("tblsync",
                columnName, null, null, null, null, "time asc"
        );

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    try {

                        MySync p = new MySync();
                        p.setId(cursor.getInt(0));
                        p.setIdRow(cursor.getInt(1));
                        p.setTableName(cursor.getString(2));
                        p.setTime(cursor.getString(3));
                        p.setAction(cursor.getString(4));

                        syncs.add(p);


                    } catch (Exception e) {
                        Log.e("Error Get Package", e.getMessage());
                    }
                    cursor.moveToNext();
                }
            }


        }
        return syncs;
    }
    boolean isRunning=true;
    public void sync(){
        new Thread(new Runnable() {
            public void run() {

                List<MySync> syncs= getAllSync();

                while(syncs.size()!=0){
                    MySync sync = syncs.remove(0);
                    MyWorker myWorker= new MyWorker();
                    myWorker.setActivity(ConnectionDatabaseLocalMobile.this.activity);
                    myWorker.setError(()->{
                        String msg = "Kết nối mạng bị lỗi.";
                        Log.e("Error", myWorker.getErrorMessengr());
                        if(ConnectionDatabaseLocalMobile.this.isRunning==false)ConnectionDatabaseLocalMobile.this.close();

                    });
                    myWorker.setSuccess(()->{
                        Log.e("sync",myWorker.getResponse() );
                        if(myWorker.getResponse().trim().equals("OK")){
                            ConnectionDatabaseLocalMobile.this.deleteSync(sync);
                            Log.e("delete", "sync"+sync.getId());
                        }
                        if(ConnectionDatabaseLocalMobile.this.isRunning==false)ConnectionDatabaseLocalMobile.this.close();
                    });
                    String key= sync.getTableName();
                    switch (key){
                        case "tblpackage":
                            myWorker.setParams(new HashMap<String,String>(){{
                                PackageDAO packageDAO= new PackageDAO(ConnectionDatabaseLocalMobile.this.activity);
                                Package p= packageDAO.getPackage(sync.getIdRow());
                                if(p.getId()==0){
                                    Log.e("Error","idpackage not found");
                                }
                                Account account= packageDAO.getAccount();
                                put("id", p.getId()+"");
                                put("color", p.getColor()+"");
                                put("title", p.getName());
                                Log.e("Date", Tool.DateToString( p.getLastEdit()));
                                put("last_edit", Tool.DateToString( p.getLastEdit()));
                                put("username", account.getUsername());
                            }});
                            if(sync.getAction().equals("insert"))
                                PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "addpackage.php");

                            break;
                        case "tblnotebook":
                            myWorker.setParams(new HashMap<String,String>(){{
                                NoteDAO noteDAO= new NoteDAO(ConnectionDatabaseLocalMobile.this.activity);
                                Notebook p= noteDAO.getNotebook(sync.getIdRow());
                                if(p.getId()==0){
                                    Log.e("Error","idnotebook not found");
                                }
                                Account account= noteDAO.getAccount();
                                put("id", p.getId()+"");
                                put("id_package", p.id_package+"");

                                put("title", p.getTitle());
                                put("content", p.getContent());

                                put("last_edit", Tool.DateToString( p.getDateEdit()));

                                put("username", account.getUsername());
                                if(p.getRemind()!=null){
                                    put("has_remind", "true");
                                    put("remind", Tool.DateToString( p.getRemind()));
                                }else
                                put("has_remind", "false");

                            }});
                            if(sync.getAction().equals("insert"))
                                PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "addnotebook.php");

                            break;

                        case "tblimage":
                            break;

                        default:
                            break;
                    }




                }







            }
        }).start();
    }

    @Override
    public synchronized void close() {
        super.close();
        this.sqLiteDatabase.close();
    }
}
