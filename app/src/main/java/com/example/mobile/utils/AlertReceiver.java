package com.example.mobile.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("time cominng","ya");
        MyNotificationHelper.notification("Hello", "How areyou?",context);
    }
}