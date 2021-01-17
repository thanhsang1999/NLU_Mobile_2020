package com.example.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageUltils {

    public static String saveToInternalStorage(Activity activity, String name, Bitmap bitmapImage){

        File directory = openOrCreateFolder(activity, "imageDir");
        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                Log.e("ultis",e.getMessage());
            }
        }
        return directory.getAbsolutePath();
    }
    public static void saveBitMapToFile(File file, Bitmap bitmapImage){


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static File openOrCreateFolder(Activity activity, String nameFolder){
        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir

        File directory = cw.getDir(nameFolder, Context.MODE_PRIVATE);
        return directory;
    }
    public static File readFileFromFolderFiles(Activity activity, String path){
        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File f= cw.getFilesDir();
        Log.e("getfiledri", f.getAbsolutePath());
        File file = new File(f, path);
        return  file;
    }
    public static File readFileFromCamera(String path){

        File f = new File( Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "QRCode");
        f.mkdirs();
        Log.e("getfiledri", f.getAbsolutePath());
        File file = new File(f, path);
        return  file;
    }
    public static File getFolderQRCode(){

        File f = new File( Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "QRCode");
        f.mkdirs();
        return  f;
    }
    public static List<Bitmap> getListBitmapFromListPath(List<String> paths){
        List<Bitmap> lst = new ArrayList<>();

        for(String path :paths){
            try {
                Bitmap bitmap= BitmapFactory.decodeFile(path);
                lst.add(bitmap);

            }catch (Exception e){
                Log.e("File not bitmap", e.getMessage());
            }
        }
        return  lst;
    }
    public static Bitmap readBitmapFromFile( File file){


            try {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());

                return  bitmap;
            }catch (Exception e){
                Log.e("File not bitmap", e.getMessage());
            }
            return null;

    }
    public static File readFile(Activity activity,String nameFolder, String path){
        File parent=openOrCreateFolder(activity, nameFolder);
        File file = new File(parent, path);
        return  file;
    }
}
