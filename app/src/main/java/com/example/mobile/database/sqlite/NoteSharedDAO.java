package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.mobile.model.InfoShare;
import com.example.mobile.model.MySync;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;

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
        ContentValues values = new ContentValues();
        values.put("title", notebook.getTitle());
        values.put("content", notebook.getContent());
        values.put("remind", Tool.DateToString(notebook.getRemind()));
        values.put("last_edit", Tool.DateToString(notebook.getDateEdit()));
        values.put("id", notebook.getId());

        boolean success=this.sqLiteDatabase.insert("tblnoteshared", null, values) != -1;



        if(success){

            if(!isSync)return true;
            this.deleteNotebook(notebook.getId(), false, true);
            MySync sync=new MySync();
            sync.setAction("insert");
            sync.setIdRow(notebook.getId());
            sync.setTableName("tblnoteshared");
            sync.setTime(Tool.DateToString(notebook.getDateEdit()));
            if(insert_sync(sync)){
                Log.e("insert","ns sync");
            }
            return true;
        }
        return false;

    }
    public Notebook getNoteShared(int id) {
        Notebook notebook =null;
        String columnName[] = {"notebook.title", "notebook.content", "notebook.last_edit", "tblpackage.color","notebook.id_package"};
        Cursor cursor = this.sqLiteDatabase.query("notebook join tblpackage on notebook.id_package=tblpackage.id",
                columnName, "notebook.id=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                notebook = new Notebook();
                try {
                    notebook.setTitle(cursor.getString(0));
                    notebook.setContent(cursor.getString(1));
                    notebook.setDateEdit(Tool.StringToDate(cursor.getString(2)));
                    notebook.setColorPackage(cursor.getString(3));
                    notebook.id_package=cursor.getInt(4);
                    notebook.setImages(getImagesByIdNotebook(id));
                    notebook.setId(id);
                } catch (Exception e) {
                    Log.e("Get notebook by id", e.getMessage());
                }
            }
        }
        return notebook;

    }
}
