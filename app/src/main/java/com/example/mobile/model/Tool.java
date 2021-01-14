package com.example.mobile.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
}
