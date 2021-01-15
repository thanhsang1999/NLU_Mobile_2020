package com.example.mobile.model;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Tool {
    private final static String prime ="Etc/UTC";
    private final static String myloace ="Asia/Ho_Chi_Minh";
    private static String paterm ="yyyy-MM-dd HH:mm:ss";
    public static Date StringToDate(String str){
        if(str==null){

            return null;
        }
        Date tmpDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(paterm);
        formatter.setTimeZone(TimeZone.getTimeZone(prime));
        try {
            tmpDate = formatter.parse(str);
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
        }
        return tmpDate;
    }
    public static String DateToString(Date date){
        if(date==null){

            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(paterm);
        formatter.setTimeZone(TimeZone.getTimeZone(prime));
        return formatter.format(date);
    }
    public static String DateToStringHCM(Date date){
        if(date==null){

            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(paterm);
        formatter.setTimeZone(TimeZone.getTimeZone(myloace));
        return formatter.format(date);
    }
    public static String DateToStringPrint(Date date){
        if(date==null){

            return null;
        }
        String patermTmp ="dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(patermTmp);

        return formatter.format(date);
    }
    public static int getDrawableByName(Context context, String name){
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
    /**true if has notebook checked ----------- false if notebooks not exists*/
    public static boolean FindCheckedInArrayList(List<Notebook> notebooks){
        for (Notebook item:notebooks) {
            if (item.getChecked()){
                return true;
            }
        }
        return false;
    }
    public static boolean LastCheckNotes(List<Notebook> notebooks){
        int count = 0;
        for (Notebook item:notebooks) {
            if (item.getChecked()){
                count++;
            }
        }
        return count==1;
    }
    public static void SetAllUnChecked(List<Notebook> notebooks){
        for (Notebook item: notebooks) {
            item.setChecked(false);
        }
    }
    public static Bitmap getBitmapFromByte(byte[] photo){
        if(photo==null)return null;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        return theImage;
    }
    public static byte[] getByteFromBitmap(Bitmap bitmap){
        if(bitmap==null)return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }
    public static Bitmap getBitmapFromBase64(String base64){
        if(base64==null)return null;
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
    }
    public static byte[] getByteFromBase64(String base64){
        if(base64==null)return null;
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);

        return  decodedString;
    }
    public static String getBase64FromBitmap(Bitmap bitmap){
        if(bitmap==null)return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return  encodedImage;
    }
    public static String getBase64FromByte(byte[] bitmap){
        if(bitmap==null)return null;

        String encodedImage = Base64.encodeToString(bitmap, Base64.DEFAULT);
        return  encodedImage;
    }


}
