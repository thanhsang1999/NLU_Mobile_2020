package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.mobile.model.AccessNoteShared;
import com.example.mobile.model.Account;
import com.example.mobile.model.MySync;
import com.example.mobile.model.NoteShared;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;

import java.util.ArrayList;
import java.util.Date;

public class NoteSharedDAO extends NoteDAO {
    public NoteSharedDAO(Activity activity) {
        super(activity);
    }



    public NoteShared insertNoteShared(Integer integer, boolean isSync) {
        Notebook notebook = this.getNotebook(integer);
        NoteShared noteShared= new NoteShared();
        Account account = getAccount();


        noteShared.setTitle(notebook.getTitle());
        noteShared.setContent(notebook.getContent());
        noteShared.setRemind(notebook.getRemind());
        noteShared.setDateEdit(new Date());
        noteShared.setAccount(account);

        noteShared= insertNoteShared(noteShared, isSync);


        if (noteShared!=null&&noteShared.getId()!=0) {


            this.deleteNotebook(notebook.getId(), false, isSync);
            return noteShared;
        }


        return null;

    }

    public NoteShared insertNoteShared(NoteShared noteShared, boolean isSync) {

        ContentValues values = new ContentValues();
        values.put("title", noteShared.getTitle());
        values.put("content", noteShared.getContent());
        values.put("remind", Tool.DateToString(noteShared.getRemind()));
        values.put("last_edit", Tool.DateToString(noteShared.getDateEdit()));

        values.put("id_account", noteShared.getAccount().getId());
        values.put("username", noteShared.getAccount().getUsername());
        if(noteShared.getId()!=0)
        values.put("id", noteShared.getId());
        boolean success = this.sqLiteDatabase.insert("tblnoteshared", null, values) > 0;
        if (success) {

            String columnName[] = {"id"};

            Cursor cursor = this.sqLiteDatabase.query("tblnoteshared",
                    columnName, "id_account=?  and last_edit=? and content=?", new String[]{noteShared.getAccount().getId() + "", Tool.DateToString(noteShared.getDateEdit()) , noteShared.getContent()}, null, null, " id desc"
            );

            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    if (!cursor.isAfterLast()) {
                        try {


                            noteShared.setId(cursor.getInt(0));
                            Log.e("GEt ID acc", "OK");

                        } catch (Exception e) {
                            Log.e("Error id access", e.getMessage());
                        }
                        cursor.moveToNext();
                    }
                }

                if (!isSync) return noteShared;

                MySync sync = new MySync();
                sync.setAction("insert");
                sync.setIdRow(noteShared.getId());
                sync.setTableName("tblnoteshared");
                sync.setTime(Tool.DateToString(new Date()));
                if (insert_sync(sync)) {
                    Log.e("insert", "ns sync");
                }


            }
            return noteShared;

        }
        return null;

    }

    public AccessNoteShared insertAccessNoteShared(AccessNoteShared accessNoteShared, boolean isSync) {

        ContentValues values = new ContentValues();
        if (accessNoteShared.getId() != 0)
            values.put("id", accessNoteShared.getId());
        values.put("id_account", accessNoteShared.getAccount().getId());
        values.put("id_noteshared", accessNoteShared.getNoteShared().getId());
        values.put("email", accessNoteShared.getAccount().getEmail());
        values.put("username", accessNoteShared.getAccount().getUsername());
        boolean success = false;
        success = this.sqLiteDatabase.insert("tblaccessnoteshared", null, values) > 0;
        if (success) {

            String columnName[] = {"id"};

            Cursor cursor = this.sqLiteDatabase.query("tblaccessnoteshared",
                    columnName, "id_account=? and id_noteshared=?", new String[]{accessNoteShared.getAccount().getId() + "", accessNoteShared.getNoteShared().getId() + ""}, null, null, null
            );

            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    if (!cursor.isAfterLast()) {
                        try {


                            accessNoteShared.setId(cursor.getInt(0));
                            Log.e("GEt ID acc", "OK");

                        } catch (Exception e) {
                            Log.e("Error id access", e.getMessage());
                        }
                        cursor.moveToNext();
                    }
                }

                if (!isSync) return accessNoteShared;

                MySync sync = new MySync();
                sync.setAction("insert");
                sync.setIdRow(accessNoteShared.getId());
                sync.setTableName("tblaccessnoteshared");
                sync.setTime(Tool.DateToString(new Date()));
                if (insert_sync(sync)) {
                    Log.e("insert", "accessnoteshared sync");
                }

                return accessNoteShared;
            }
            return null;

        }
        return null;
    }
    public AccessNoteShared getAccessNoteShared( int id) {
        AccessNoteShared accessNoteShared= null;

        String columnName[] = {"id_account","username","email","id_noteshared"};

        Cursor cursor = this.sqLiteDatabase.query("tblaccessnoteshared",
                columnName, "id=?", new String[]{id + ""}, null, null, null
        );

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                if (!cursor.isAfterLast()) {
                    try {
                        Account account=new Account(cursor.getInt(0));
                        account.setUsername(cursor.getString(1));
                        account.setEmail(cursor.getString(2));
                        NoteShared notebook=new NoteShared();
                        notebook.setId(cursor.getInt(3));
                        accessNoteShared=new AccessNoteShared(account);
                        accessNoteShared.setNoteShared(notebook);
                        accessNoteShared.setId(id);

                        Log.e("GEt ID acc", "OK");

                    } catch (Exception e) {
                        Log.e("Error id access", e.getMessage());
                    }
                    cursor.moveToNext();
                }
            }



            return accessNoteShared;

        }
        return accessNoteShared;
    }

    public NoteShared getNoteShared(int id) {
        if(id==0) {
            Log.e("Not get id", "= "+id);
            return  null;
        }

        NoteShared notebook = null;
        String columnName[] = {"title", "content", "last_edit", "remind", "id_account", "username"};
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
                    Account account = new Account(cursor.getInt(4));
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
        Account currentAccount=getAccount();
        if(currentAccount==null){
            return new ArrayList<>();
        }
        String sqlLimit = "";
        if (limit != -1) {
            sqlLimit = " limit " + limit;
        }

        String query = "SELECT id,title, content,last_edit, remind, id_account, username FROM tblnoteshared "+" where id_account ="+currentAccount.getId()+" order by last_edit desc" + sqlLimit;
        ArrayList<NoteShared> notebooks = new ArrayList<>();
        Cursor cursor = GetData(query);
        while (cursor.moveToNext()) {
            NoteShared notebook = new NoteShared();
            notebook.setId(cursor.getInt(0));
            notebook.setTitle(cursor.getString(1));
            notebook.setContent(cursor.getString(2));
            notebook.setDateEdit(Tool.StringToDate(cursor.getString(3)));
            notebook.setRemind(Tool.StringToDate(cursor.getString(4)));
            Account account = new Account(cursor.getInt(5));
            account.setUsername(cursor.getString(6));
            notebook.setAccount(account);
            notebooks.add(notebook);

        }
        return notebooks;
    }
    public ArrayList<NoteShared> getAccessNoteSharedLast(int limit) {
        Account currentAccount=getAccount();
        if(currentAccount==null){
            return new ArrayList<>();
        }
        String sqlLimit = "";
        if (limit != -1) {
            sqlLimit = " limit " + limit;
        }

        String query = "SELECT tblnoteshared.id,tblnoteshared.title, tblnoteshared.content,tblnoteshared.last_edit, tblnoteshared.remind, tblnoteshared.id_account, tblnoteshared.username FROM tblnoteshared join tblaccessnoteshared on tblnoteshared.id=tblaccessnoteshared.id_noteshared where tblnoteshared.id_account != "+currentAccount.getId()+" order by tblnoteshared.last_edit desc" + sqlLimit;
        ArrayList<NoteShared> notebooks = new ArrayList<>();
        Cursor cursor = GetData(query);
        while (cursor.moveToNext()) {
            NoteShared notebook = new NoteShared();
            notebook.setId(cursor.getInt(0));
            notebook.setTitle(cursor.getString(1));
            notebook.setContent(cursor.getString(2));
            notebook.setDateEdit(Tool.StringToDate(cursor.getString(3)));
            notebook.setRemind(Tool.StringToDate(cursor.getString(4)));
            Account account = new Account(cursor.getInt(5));
            account.setUsername(cursor.getString(6));
            notebook.setAccount(account);
            notebooks.add(notebook);

        }
        return notebooks;
    }
}
