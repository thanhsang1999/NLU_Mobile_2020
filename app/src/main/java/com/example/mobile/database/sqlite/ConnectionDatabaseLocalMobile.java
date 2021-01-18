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
import com.example.mobile.model.AccessNoteShared;
import com.example.mobile.model.Account;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.model.MyImage;
import com.example.mobile.model.MySync;
import com.example.mobile.model.NoteShared;
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
                "password TEXT," +
                "gender TEXT," +
                "outside TEXT," +
                "id_outside TEXT," +
                "dateofbirth TEXT" +

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
                "image TEXT,"+
                "last_edit TEXT);";
        sqLiteDatabase.execSQL(sqlCreateTableImage);

        String sqlCreateTableSync = "CREATE TABLE IF NOT EXISTS tblsync ("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "id_row integer," +
                "action_sync TEXT,"+
                "name_table TEXT,"+
                "time TEXT);";
        sqLiteDatabase.execSQL(sqlCreateTableSync);
        String sqlCreateTableNoteshared = "CREATE TABLE IF NOT EXISTS tblnoteshared ("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "id_account integer," +
                "username TEXT,"+
                "title TEXT,"+
                "last_edit TEXT,"+
                "content TEXT,"+
                "remind TEXT);";
        sqLiteDatabase.execSQL(sqlCreateTableNoteshared);
        String sqlCreateTableAccessshared = "CREATE TABLE IF NOT EXISTS tblaccessnoteshared ("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "id_noteshared integer," +
                "username text," +
                "email text," +
                "id_account integer);";
        sqLiteDatabase.execSQL(sqlCreateTableAccessshared);



    }

    public void earse() {

        String sql = "DELETE FROM tblaccounts;";
        String sql2 = "DELETE FROM tblpackage";
        String sql3 = "DELETE FROM notebook";
        String sql4 = "DELETE FROM tblsync";
        String sql5 = "DELETE FROM tblnoteshared";
        String sql6 = "DELETE FROM tblaccessnoteshared";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
        sqLiteDatabase.execSQL(sql6);
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
        long ret = this.sqLiteDatabase.insert("tblsync", null, values) ;
        Log.e("rs", ret+"");
        if(Long.compare(ret,1)==0){
            Log.e("Insert Sync", "Ok" );
        }
        return Long.compare(ret,1)==0;
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
                columnName, null, null, null, null, "id asc"
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
    public Account getAccount() {

        String columnName[] = {"username", "fullname", "email", "password","gender","dateofbirth","outside","id_outside","id"};

        Cursor cursor = this.sqLiteDatabase.query("tblaccounts",
                columnName, null, null, null, null, null
        );

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                if (cursor.getCount() == 1)
                    try {

                        Account account = new Account(cursor.getInt(4),cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                        account.setGender(cursor.getString(4));
                        account.setDateOfBirth(Tool.StringToDate(cursor.getString(5)));
                        account.setOutside(cursor.getString(6));
                        account.setIdOutSide(cursor.getString(7));
                        account.setId(cursor.getInt(8));
                        Log.e("Account", account.getUsername());
                        return account;
                    } catch (Exception e) {
                        Log.e("Exception", e.getMessage().toString());
                    }


            }

        }
        Log.e("GetAccount", "Null");


        return null;
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
                    String keyAction= sync.getAction();
                    Log.e("TableName", key);
                    Log.e("ActionSync", keyAction);
                    switch (key){
                        case "tblpackage":
                            myWorker.setParams(new HashMap<String,String>(){{
                                PackageDAO packageDAO= new PackageDAO(ConnectionDatabaseLocalMobile.this.activity);
                                Package p= packageDAO.getPackage(sync.getIdRow());
                                if(p.getId()==0){
                                    Log.e("Error","idpackage not found");
                                }
                                Account account= packageDAO.getAccount();
                                Log.e("id",p.getId()+"");
                                put("id", p.getId()+"");
                                put("color", p.getColor()+"");
                                put("title", p.getName());

                                put("last_edit", Tool.DateToString( p.getLastEdit()));
                                put("username", account.getUsername());
                            }});

                            switch (keyAction){
                                case "insert":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "addpackage.php");
                                    break;
                                case "update":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "updatepackage.php");
                                    break;
                                default:
                                    break;
                            }



                            break;
                            case "tblaccount":
                            myWorker.setParams(new HashMap<String,String>(){{
                                AccountDAO packageDAO= new AccountDAO(ConnectionDatabaseLocalMobile.this.activity);

                                Account account= packageDAO.getAccount();


                                put("gender", account.getGender());
                                put("dateofbirth", Tool.DateToString(account.getDateOfBirth()));

                                put("fullname", account.getFullname());
                                put("username", account.getUsername());
                                put("email", account.getEmail());
                                Log.e("username", account.getUsername());
                            }});

                            switch (keyAction){

                                case "update":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "updateaccount.php");
                                    break;
                                default:
                                    break;
                            }



                            break;
                        case "tblnotebook":
                            myWorker.setParams(new HashMap<String,String>(){{
                                NoteDAO noteDAO= new NoteDAO(ConnectionDatabaseLocalMobile.this.activity);
                                Account account= noteDAO.getAccount();
                                put("id", sync.getIdRow()+"");
                                put("id_account", account.getId()+"");
                                Log.e("id", sync.getIdRow()+"");
                                Log.e("id_account", account.getId()+"");

                                if(!keyAction.equals("delete")){
                                    Notebook p= noteDAO.getNotebook(sync.getIdRow());
                                    if(p.getId()==0){
                                        Log.e("Error","idnotebook not found");
                                    }

                                    put("id", p.getId()+"");
                                    put("id_package", p.id_package+"");

                                    put("title", p.getTitle());
                                    put("content", p.getContent());

                                    put("last_edit", Tool.DateToString( p.getDateEdit()));

                                    put("id_account", account.getId()+"");
                                    if(p.getRemind()!=null){
                                        put("has_remind", "true");
                                        put("remind", Tool.DateToString( p.getRemind()));
                                    }else
                                        put("has_remind", "false");

                                }


                            }});

                            switch (keyAction){
                                case "insert":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "addnotebook.php");
                                    break;
                                case "delete":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "deletenotebook.php");
                                    break;
                                case "update":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "updatenotebook.php");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "tblnoteshared":
                            myWorker.setParams(new HashMap<String,String>(){{
                                NoteSharedDAO noteDAO= new NoteSharedDAO(ConnectionDatabaseLocalMobile.this.activity);
                                NoteShared p= noteDAO.getNoteShared(sync.getIdRow());
                                if(p.getId()==0){
                                    Log.e("Error","idnoteshared not found");
                                }

                                put("id", p.getId()+"");
                                Log.e("id", p.getId()+"");

                                put("title", p.getTitle());
                                put("content", p.getContent());
                                put("last_edit", Tool.DateToString( p.getDateEdit()));
                                put("id_account", p.getAccount().getId()+"");
                                put("username", p.getAccount().getUsername()+"");
                                if(p.getRemind()!=null){
                                    put("has_remind", "true");
                                    put("remind", Tool.DateToString( p.getRemind()));
                                }else
                                    put("has_remind", "false");

                            }});

                            switch (keyAction){
                                case "insert":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "addnoteshared.php");
                                    break;
                                case "update":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "updatenoteshared.php");
                                    break;
                                default:
                                    break;
                            }
                            break;

                        case "tblaccessnoteshared":
                            myWorker.setParams(new HashMap<String,String>(){{
                                NoteSharedDAO noteDAO= new NoteSharedDAO(ConnectionDatabaseLocalMobile.this.activity);
                                AccessNoteShared p= noteDAO.getAccessNoteShared(sync.getIdRow());
                                if(p.getId()==0){
                                    Log.e("Error","idaccessnoteshared not found");
                                }

                                put("id", p.getId()+"");
                                Log.e("Id",""+p.getId());
                                put("id_noteshared", p.getNoteShared().getId()+"");
                                Log.e("IdNoteshared",""+p.getId());
                                put("email", p.getAccount().getEmail());
                                Log.e("email", p.getAccount().getEmail()+"");
                                put("id_account", p.getAccount().getId()+"");
                                Log.e("IdAccount", p.getAccount().getId()+"");
                                put("username", p.getAccount().getUsername()+"");
                                Log.e("Username", p.getAccount().getUsername()+"");


                            }});

                            switch (keyAction){
                                case "insert":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "addaccessnoteshared.php");
                                    break;

                                default:
                                    break;
                            }
                            break;

                        case "tblimage":
                            myWorker.setParams(new HashMap<String,String>(){{
                                NoteDAO noteDAO= new NoteDAO(ConnectionDatabaseLocalMobile.this.activity);
                                MyImage p= noteDAO.getImage(sync.getIdRow());
                                if(p.getId()==0){
                                    Log.e("Error","idimage not found");
                                }
                                Account account= noteDAO.getAccount();
                                put("id", p.getId()+"");
                                put("id_notebook", p.idNotebook+"");
                                put("image", p.getImage());
                                put("last_edit", Tool.DateToString( p.getLastEdit()));
                                put("username", account.getUsername());


                            }});
                            switch (keyAction){
                                case "insert":
                                    PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "addimagenotebook.php");
                                break;
                                default:
                                break;
                            }

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
