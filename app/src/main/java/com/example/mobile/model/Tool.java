package com.example.mobile.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tool {
    private static String paterm ="yyyy-MM-dd HH:mm:ss";
    public static Date StringToDate(String str){
        Date tmpDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(paterm);
        try {
            tmpDate = formatter.parse(str);
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
        }
        return tmpDate;
    }
    public static String DateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(paterm);
        return formatter.format(date);
    }
    public static String DateToStringPrint(Date date){
        String patermTmp ="dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(patermTmp);
        return formatter.format(date);
    }
    public static int getDrawableByName(Context context, String name){
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
    /**true if has notebook checked ----------- false if notebooks not exists*/
    public static boolean FindCheckedInArrayList(ArrayList<Notebook> notebooks){
        for (Notebook item:notebooks) {
            if (item.getChecked()){
                return true;
            }
        }
        return false;
    }
}
