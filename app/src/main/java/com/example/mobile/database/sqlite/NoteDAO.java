package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteDAO  extends PackageDAO {
    public NoteDAO(Activity activity) {
        super(activity);
    }
    public Bitmap getImage(int id) {
        byte[] photo=null;
        String columnName[] = {"images_note.id", "images_note.image"};
        Cursor cursor = this.sqLiteDatabase.query("images_note",
                columnName, "images_note.id=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                try {
                   photo=cursor.getBlob(1);
                } catch (Exception e) {
                    Log.e("Get image by id", e.getMessage());
                }
            }
        }
        if(photo==null){
            Log.e("not byte", "ok");
            return null;
        }
        ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);

        return theImage;
    }
    public List<Bitmap> getImagesByIdNotebook(int idNotebook) {
        Log.e("checkid", "idNote"+idNotebook+"");
        List<Bitmap> bitmaps= new ArrayList<>();
        String columnName[] = {"images_note.id", "images_note.image"};
        Cursor cursor = this.sqLiteDatabase.query("images_note",
                columnName, "images_note.id_notebook=?", new String[]{String.valueOf(idNotebook)},
                null, null, "images_note.id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {

                try {
                    byte[] photo=null;
                    photo=cursor.getBlob(1);
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                    Bitmap theImage= BitmapFactory.decodeStream(imageStream);
                    bitmaps.add(theImage);
                } catch (Exception e) {
                    Log.e("Get image by id", e.getMessage());
                }
            }
        }



        return bitmaps;
    }
    public boolean insertImage(int idNotebook, Bitmap bitmap, Date d) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();



        ContentValues values = new ContentValues();

        values.put("id_notebook", idNotebook);
        values.put("image", byteArray);
        values.put("last_edit", Tool.DateToString(d));

        boolean success=this.sqLiteDatabase.insert("images_note", null, values) != -1;


        Log.e("Insert Image", "" + success);

        return success;
    }

    public Notebook insertNotebook(Notebook n, int idPackage) {

        if(idPackage==0){
            idPackage=getLastPackage().getId();
        }

        ContentValues values = new ContentValues();
        values.put("title", n.getTitle());
        values.put("content", n.getContent());
        values.put("id_package", idPackage);
        values.put("remind", Tool.DateToString(n.getRemind()));
        values.put("last_edit", Tool.DateToString(n.getDateEdit()));

        boolean success=this.sqLiteDatabase.insert("notebook", null, values) != -1;
        int ret=updatePackageDateEdit(idPackage, n.getDateEdit());

        Log.e("Insert Notebook", "" + success);
        Log.e("Update PackageEditTime", "" + (ret==1));
        Notebook notebook=getNotebooksLast(1).get(0);
        int countI=1;
        for(Bitmap b:n.getImages()){
            insertImage(notebook.getId(),b,notebook.getDateEdit());
            Log.e("Insert Image", ""+(countI++));
        }




//        new Thread(()->{
//            ConnectionWebService connectionWebService= new ConnectionWebService(this.activity);
//            connectionWebService.insert_package(p, getAccount());
//        }).start();
        return getNotebooksLast(1).get(0);
    }
    public int updateNotebook(Notebook notebook, int idPackage){
        ContentValues values = new ContentValues();
        values.put("last_edit",Tool.DateToString(new Date()));
        notebook.setDateEdit(new Date());
        values.put("title",notebook.getTitle());
        values.put("content",notebook.getContent());


        int ret = this.sqLiteDatabase.update("notebook", values, "id=?", new String[]{notebook.getId()+""});
        updateImages(notebook.getId(),notebook.getImages(),notebook.getDateEdit());
        if(ret==1){
            updatePackageDateEdit(idPackage,new Date());
            Log.e("updatenote","ok");
        }

        return ret;
    }
    public void updateImages(int idNotebook, List<Bitmap> bitmaps, Date d){

        List<Bitmap> bitmaps2= new ArrayList<>(bitmaps);

        String columnName[] = {"images_note.id"};
        Cursor cursor = this.sqLiteDatabase.query("images_note",
                columnName, "images_note.id_notebook=?", new String[]{String.valueOf(idNotebook)},
                null, null, "id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                   updateImage(cursor.getInt(0),bitmaps2.remove(0),d);
                } catch (Exception e) {
                    Log.e("Get image by id", e.getMessage());
                }
            }
        }
        while(bitmaps2.size()!=0){
            insertImage(idNotebook,bitmaps2.remove(0),d);
        }
    }
    public boolean updateImage(int id, Bitmap bitmap, Date d){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();


        ContentValues values = new ContentValues();
        values.put("last_edit",Tool.DateToString(d));
        values.put("image",byteArray);
        int ret = this.sqLiteDatabase.update("images-note", values, "id=?", new String[]{id+""});
        return ret==1;
    }


    public ArrayList<Notebook> getNotebooksLast(int limit) {
        String sqlLimit="";
        if(limit!=-1){
            sqlLimit=" limit "+limit;
        }

        String query = "SELECT notebook.id,notebook.title, notebook.content,notebook.last_edit, tblpackage.color,notebook.remind FROM notebook join tblpackage on notebook.id_package = tblpackage.id order by notebook.last_edit desc"+sqlLimit;
        ArrayList<Notebook> notebooks = new ArrayList<>();
        Cursor cursor = GetData(query);
        while (cursor.moveToNext()){
            Notebook notebook = new Notebook();
            notebook.setId(cursor.getInt(0));
            notebook.setTitle(cursor.getString(1));
            notebook.setContent(cursor.getString(2));
            notebook.setDateEdit(Tool.StringToDate(cursor.getString(3)));
            notebook.setColorPackage(cursor.getString(4));
            notebook.setRemind(Tool.StringToDate(cursor.getString(5)));


            notebooks.add(notebook);

        }
        return notebooks;
    }

    public Notebook getNotebook(int id) {
        Notebook notebook =null;
        String columnName[] = {"notebook.title", "notebook.content", "notebook.last_edit", "tblpackage.color"};
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
