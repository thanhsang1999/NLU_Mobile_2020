package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mobile.model.MyImage;
import com.example.mobile.model.MySync;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
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
    public MyImage getImage(int id) {
        MyImage myImage=null;
        String columnName[] = {"images_note.id", "images_note.id_notebook","images_note.image", "images_note.last_edit"};
        Cursor cursor = this.sqLiteDatabase.query("images_note",
                columnName, "images_note.id=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                try { myImage= new MyImage();
                    myImage.setId(cursor.getInt(0));
                    myImage.idNotebook= cursor.getInt(1);
                    myImage.setLastEdit(Tool.StringToDate(cursor.getString(3)));
                    myImage.setImage(cursor.getBlob(2));
                } catch (Exception e) {
                    Log.e("Get image by id", e.getMessage());
                }
            }
        }





        return myImage;
    }
    public List<byte[]> getImagesByIdNotebook(int idNotebook) {
        Log.e("checkid", "idNote"+idNotebook+"");
        List<byte[]> bitmaps= new ArrayList<>();
        String columnName[] = {"images_note.id", "images_note.image"};
        Cursor cursor = this.sqLiteDatabase.query("images_note",
                columnName, "images_note.id_notebook=?", new String[]{String.valueOf(idNotebook)},
                null, null, "images_note.id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {

                try {
                    byte[]
                    photo=cursor.getBlob(1);

                    bitmaps.add(photo);
                } catch (Exception e) {
                    Log.e("Get image by id", e.getMessage());
                }
            }
        }



        return bitmaps;
    }
    public boolean insertImage(int idNotebook, byte[] bitmap, Date d, boolean isSyns) {





        ContentValues values = new ContentValues();

        values.put("id_notebook", idNotebook);
        values.put("image", bitmap);
        values.put("last_edit", Tool.DateToString(d));

        boolean success=this.sqLiteDatabase.insert("images_note", null, values) != -1;


        Log.e("Insert Image", "" + success);
        if(success){
            MyImage myImage=getImagesLast(1).get(0);
            if(!isSyns)return success;
            MySync sync=new MySync();
            sync.setAction("insert");
            sync.setIdRow(myImage.getId());
            sync.setTableName("tblnotebook");
            sync.setTime(Tool.DateToString(d));
            if(insert_sync(sync)){
                Log.e("insert","i sync");
            }
//
//            new Thread(()->{
//                ConnectionWebService connectionWebService= new ConnectionWebService(this.activity);
//                connectionWebService.insert_package(p2, getAccount());
//            }).start();

        }
        return success;
    }

    public Notebook insertNotebook(Notebook n, int idPackage, boolean isSyns) {

        if(idPackage==0){
            idPackage=getLastPackage().getId();
        }

        ContentValues values = new ContentValues();
        values.put("title", n.getTitle());
        values.put("content", n.getContent());
        values.put("id_package", idPackage);
        values.put("remind", Tool.DateToString(n.getRemind()));
        values.put("last_edit", Tool.DateToString(n.getDateEdit()));
        if(n.getId()!=0){
            values.put("id", n.getId());
        }

        boolean success=this.sqLiteDatabase.insert("notebook", null, values) != -1;
        int ret=updatePackageDateEdit(idPackage, n.getDateEdit());

        Log.e("Insert Notebook", "" + success);
        Log.e("Update PackageEditTime", "" + (ret==1));
        Notebook notebook=n;
        if(n.getId()==0)
         notebook=getNotebooksLast(1).get(0);
        if(success){

            if(!isSyns)return notebook;
            MySync sync=new MySync();
            sync.setAction("insert");
            sync.setIdRow(notebook.getId());
            sync.setTableName("tblnotebook");
            sync.setTime(Tool.DateToString(notebook.getDateEdit()));
            if(insert_sync(sync)){
                Log.e("insert","n sync");
            }
        }
        int countI=1;
        for(byte[] b:n.getImages()){
            insertImage(notebook.getId(),b,notebook.getDateEdit(), true);
            Log.e("Insert Image", ""+(countI++));
        }




//        new Thread(()->{
//            ConnectionWebService connectionWebService= new ConnectionWebService(this.activity);
//            connectionWebService.insert_package(p, getAccount());
//        }).start();
        return getNotebooksLast(1).get(0);
    }
    public int updateNotebook(Notebook notebook, int idPackage,boolean isSync){
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
            MySync sync=new MySync();
            sync.setAction("update");
            sync.setIdRow(notebook.getId());
            sync.setTableName("tblnotebook");
            sync.setTime(Tool.DateToString(notebook.getDateEdit()));
            if(insert_sync(sync)){
                Log.e("sync","un");
            }
        }

        return ret;
    }
    public void updateImages(int idNotebook, List<byte[]> bitmaps, Date d){

        List<byte[]> bitmaps2= new ArrayList<>(bitmaps);

        String columnName[] = {"images_note.id"};
        Cursor cursor = this.sqLiteDatabase.query("images_note",
                columnName, "images_note.id_notebook=?", new String[]{String.valueOf(idNotebook)},
                null, null, "id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                   updateImage(cursor.getInt(0),bitmaps2.remove(0),d,true);
                } catch (Exception e) {
                    Log.e("Get image by id", e.getMessage());
                }
            }
        }
        while(bitmaps2.size()!=0){
            insertImage(idNotebook,bitmaps2.remove(0),d, true);
        }
    }
    public boolean updateImage(int id, byte[] bitmap, Date d, boolean isSync){



        ContentValues values = new ContentValues();
        values.put("last_edit",Tool.DateToString(d));
        values.put("image",bitmap);
        int ret = this.sqLiteDatabase.update("images_note", values, "id=?", new String[]{id+""});
        if(ret==1){
            if(!isSync)return true;

            MySync sync=new MySync();
            sync.setAction("update");
            sync.setIdRow(id);
            sync.setTableName("tblimage");
            sync.setTime(Tool.DateToString(d));
            if(insert_sync(sync)){
                Log.e("update","i sync");
            }
        }
        return true;
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
    public ArrayList<MyImage> getImagesLast(int limit) {
        String sqlLimit="";
        if(limit!=-1){
            sqlLimit=" limit "+limit;
        }

        String query = "SELECT id,id_notebook, image, last_edit from images_note  order by last_edit desc "+sqlLimit;
        ArrayList<MyImage> notebooks = new ArrayList<>();
        Cursor cursor = GetData(query);
        while (cursor.moveToNext()){
            MyImage notebook = new MyImage();
            notebook.setId(cursor.getInt(0));
            notebook.idNotebook=cursor.getInt(1);
            notebook.setImage(cursor.getBlob(2));
            notebook.setLastEdit(Tool.StringToDate(cursor.getString(3)));



            notebooks.add(notebook);

        }
        return notebooks;
    }

    public Notebook getNotebook(int id) {
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
    public boolean DeleteNotebook(int id){
        this.sqLiteDatabase.delete("notebook","id=?",new String[]{String.valueOf(id)});
        this.sqLiteDatabase.delete("images_note","id_notebook=?",new String[]{String.valueOf(id)});
        return true;
    }
    public boolean deleteNotebook(int id, boolean deleteImage, boolean isSync){
        int ret=this.sqLiteDatabase.delete("notebook","id=?",new String[]{String.valueOf(id)});
        if(deleteImage)
        this.sqLiteDatabase.delete("images_note","id_notebook=?",new String[]{String.valueOf(id)});
        Log.e("ret", ret+"");
        if(ret==1){
            if(!isSync)return true;

            MySync sync=new MySync();
            sync.setAction("delete");
            sync.setIdRow(id);
            sync.setTableName("tblnotebook");
            sync.setTime(Tool.DateToString(new Date()));
            if(insert_sync(sync)){
                Log.e("insert","dn sync");
            }
            return true;
        }
        return false;
    }


}
