package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.mobile.model.Account;
import com.example.mobile.model.InfoShare;
import com.example.mobile.model.MySync;
import com.example.mobile.model.NoteShared;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteSharedDAO extends  NoteDAO {
    public NoteSharedDAO(Activity activity) {
        super(activity);
    }

    public void shared(InfoShare i, List<Integer> lstShared) {
        Log.e("shared name", i.getName());
        Log.e("shared email", i.getEmail());
        Log.e("lstShared", "size= "+lstShared.size());
    }

    public boolean insertNoteShared(Integer integer, boolean isSync) {
        Notebook notebook = this.getNotebook(integer);
        Account account=getAccount();
        ContentValues values = new ContentValues();
        values.put("title", notebook.getTitle());
        values.put("content", notebook.getContent());
        values.put("remind", Tool.DateToString(notebook.getRemind()));
        values.put("last_edit", Tool.DateToString(notebook.getDateEdit()));
        values.put("id", notebook.getId());
        values.put("id_account", account.getId());
        values.put("username", account.getUsername());
        boolean success=this.sqLiteDatabase.insert("tblnoteshared", null, values) != -1;



        if(success){

            if(!isSync)return true;

            MySync sync=new MySync();
            sync.setAction("insert");
            sync.setIdRow(notebook.getId());
            sync.setTableName("tblnoteshared");
            sync.setTime(Tool.DateToString(new Date()));
            if(insert_sync(sync)){
                Log.e("insert","ns sync");
            }
            this.deleteNotebook(notebook.getId(), false, true);
            return true;
        }
        return false;

    }
    public boolean insertNoteShared(NoteShared noteShared, boolean isSync) {

        ContentValues values = new ContentValues();
        values.put("title", noteShared.getTitle());
        values.put("content", noteShared.getContent());
        values.put("remind", Tool.DateToString(noteShared.getRemind()));
        values.put("last_edit", Tool.DateToString(noteShared.getDateEdit()));
        values.put("id", noteShared.getId());
        values.put("id_account", noteShared.getAccount().getId());
        values.put("username", noteShared.getAccount().getUsername());
        boolean success=this.sqLiteDatabase.insert("tblnoteshared", null, values)>0;
        if(success){


            return true;
        }
        return false;

    }
    public NoteShared getNoteShared(int id) {
        NoteShared notebook =null;
        String columnName[] = {"title", "content", "last_edit", "remind","id_account","username"};
        Cursor cursor = this.sqLiteDatabase.query("tblnoteshared",
                columnName, "id=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                notebook = new NoteShared();
                try {
                    notebook.setTitle(cursor.getString(0));
                    notebook.setContent(cursor.getString(1));
                    notebook.setDateEdit(Tool.StringToDate(cursor.getString(2)));
                    notebook.setRemind(Tool.StringToDate(cursor.getString(3)));
                    Account account= new Account(cursor.getInt(4));
                    account.setUsername(cursor.getString(5));
                    notebook.setAccount(account);
                    //notebook.setImages(getImagesByIdNotebook(id));
                    notebook.setId(id);
                } catch (Exception e) {
                    Log.e("Get notebook by id", e.getMessage());
                }
            }
        }
        return notebook;

    }



    public ArrayList<NoteShared> getNoteSharedLast(int limit) {
        String sqlLimit="";
        if(limit!=-1){
            sqlLimit=" limit "+limit;
        }

        String query = "SELECT id,title, content,last_edit, remind, id_account, username FROM tblnoteshared order by last_edit desc"+sqlLimit;
        ArrayList<NoteShared> notebooks = new ArrayList<>();
        Cursor cursor = GetData(query);
        while (cursor.moveToNext()){
            NoteShared notebook = new NoteShared();
            notebook.setId(cursor.getInt(0));
            notebook.setTitle(cursor.getString(1));
            notebook.setContent(cursor.getString(2));
            notebook.setDateEdit(Tool.StringToDate(cursor.getString(3)));
            notebook.setRemind(Tool.StringToDate(cursor.getString(4)));
            Account account=new Account(cursor.getInt(5));
            account.setUsername(cursor.getString(6));
            notebook.setAccount(account);
            notebooks.add(notebook);

        }
        return notebooks;
    }
}
