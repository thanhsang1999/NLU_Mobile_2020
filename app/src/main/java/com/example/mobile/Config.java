package com.example.mobile;

import android.util.Log;

public class Config {
    public static boolean IS_LOCAL = false;
    public static String URL_LOCAL = "http://192.168.1.17/mobile/";
    public static String URL_GLOBAL = "https://mobilenlu2020.000webhostapp.com/";
    public static String getURL(){
        Log.e("DATABASE",  (IS_LOCAL)? URL_LOCAL:URL_GLOBAL);
        return (IS_LOCAL)? URL_LOCAL:URL_GLOBAL;
    };

}
