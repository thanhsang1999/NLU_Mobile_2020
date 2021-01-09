package com.example.mobile.webservice.ultils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.mobile.Config;
import com.example.mobile.HomeActivity;
import com.example.mobile.LogoActivity;
import com.example.mobile.WellComeActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class ExecuteRawSQLFromWebServie {
    public static void executeQueryRaw(String raw, Activity activity){
        MyWorker myWorker= new MyWorker();
        myWorker.setActivity(activity);
        myWorker.setError(()->{
            String msg = "Kết nối mạng bị lỗi.";
            Log.e("Error", myWorker.getErrorMessengr());
            Toast.makeText( myWorker.getActivity(), msg, Toast.LENGTH_SHORT).show();

        });
        myWorker.setSuccess(()->{
            Log.e("Result execute raw",myWorker.getResponse() );


            IRawSQL rawSQL= (IRawSQL) myWorker.getActivity();

            try {
                JSONArray jsonArray = new JSONArray(myWorker.getResponse().toString());
                rawSQL.acceptReturnJSONArray(jsonArray);
                return;
            } catch (JSONException e) {
                Log.e("Error:jsonArray", e.getMessage());
                return;
            }


        });
        myWorker.setParams(new HashMap<String,String>(){{
            put("raw",raw);
        }});

        PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "executequerryraw.php");

    }
    public static void executeUpdateRaw(String raw, Activity activity){
        MyWorker myWorker= new MyWorker();
        myWorker.setActivity(activity);
        myWorker.setError(()->{
            String msg = "Kết nối mạng bị lỗi.";
            Log.e("Error", myWorker.getErrorMessengr());
            Toast.makeText( myWorker.getActivity(), msg, Toast.LENGTH_SHORT).show();

        });
        myWorker.setSuccess(()->{
            Log.e("Result execute raw",myWorker.getResponse().trim());
            int rowAffect=0;

            IRawSQL rawSQL= (IRawSQL) myWorker.getActivity();
            try{
                rowAffect=Integer.parseInt(myWorker.getResponse().trim());
                rawSQL.acceptReturnRowAffect(rowAffect);
                return;
            }catch (Exception e){
                Log.e("Error:parseInt", e.getMessage());
            }



        });
        myWorker.setParams(new HashMap<String,String>(){{
            put("raw",raw);
        }});

        PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "executeupdateraw.php");

    }
}
