package com.example.mobile.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringConverter  {
    private final String regex ="^\\D{4}-\\D{2}-\\D{2} \\D{2}:\\D{2}:\\D{2}.\\D{3}$";
    private final String paterm ="yyyy-MM-dd HH:mm:ss";
    public Date date;
    public DateStringConverter(Date date){
        this.date=date;
    }
    public DateStringConverter(String str){
        SimpleDateFormat formatter = new SimpleDateFormat(paterm);
        try {
           this.date = formatter.parse(str);

        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
        }

    }
    public String getText(){
        SimpleDateFormat formatter = new SimpleDateFormat(paterm);
        String strDate = formatter.format(date);
        return strDate;
    }
    public Date getDate(){

        return this.date;
    }
}
