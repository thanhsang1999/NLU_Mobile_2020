package com.example.mobile.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Date;

public class ExactThreadHelper {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void  hel(Context context, Date date, String title, String content){
        Log.e("ExactThreadHelper","Run");
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, AlertReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
//        PendingIntent pendingIntent =
//                PendingIntent.getService(context, requestId, intent,
//                        PendingIntent.FLAG_NO_CREATE);
//        if (pendingIntent != null && alarmManager != null) {
//            alarmManager.cancel(pendingIntent);
//        }
    }
}
