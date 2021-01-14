package com.example.mobile;

import android.util.Log;

public class Config {
    public static boolean IS_LOCAL = false;
    public static String URL_LOCAL_SANG = "http://192.168.0.222:8080/mobile/";
    public static String URL_LOCAL_SANG2 = "http://192.168.137.1:8080/mobile";
    public static String URL_LOCAL = "http://192.168.0.111/mobile/";
    public static String URL_GLOBAL = "https://mobilenlu2020.000webhostapp.com/";
    public static String getURL(){
        Log.e("DATABASE",  (IS_LOCAL)? URL_LOCAL_SANG2:URL_GLOBAL);
        return (IS_LOCAL)? URL_LOCAL_SANG:URL_GLOBAL;
    };

}
