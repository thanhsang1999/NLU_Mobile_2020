package com.example.mobile.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class ExactThreadHelper {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void  hel(Context context, Calendar c){
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        PendingIntent pendingIntent =
//                PendingIntent.getService(context, requestId, intent,
//                        PendingIntent.FLAG_NO_CREATE);
//        if (pendingIntent != null && alarmManager != null) {
//            alarmManager.cancel(pendingIntent);
//        }
    }
}
