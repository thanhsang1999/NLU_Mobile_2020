package com.example.mobile.sharepreference;

import android.content.Context;

public class DataLocalManager {
    private static DataLocalManager instance;
    private MySharedPreferences sharedPreferences;

    public static  void init(Context context){
        instance = new DataLocalManager();
        instance.sharedPreferences = new MySharedPreferences(context);
    }
    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
}
