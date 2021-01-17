package com.example.mobile.database.webservice;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.mobile.Config;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.LogoActivity;
import com.example.mobile.activity.WellComeActivity;
import com.example.mobile.adapter.ShareNoteAdapter;
import com.example.mobile.model.InfoShare;
import com.example.mobile.webservice.ultils.MyWorker;
import com.example.mobile.webservice.ultils.PrepareConnectionWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AccountServiceHelper extends  ConnectionWebService {
    public AccountServiceHelper(Activity activity) {
        super(activity);
    }
    public void getUsernameAndEmail(String usernameOrEmail, ShareNoteAdapter shareNoteAdapter){
        new Thread(new Runnable() {
            public void run() {

                //Thread.sleep(2000);
                MyWorker myWorker= new MyWorker();
                myWorker.setActivity(AccountServiceHelper.this.activity);
                myWorker.setError(()->{
                    String msg = "Kết nối mạng bị lỗi.";
                    Log.e("Error", myWorker.getErrorMessengr());

                    Toast.makeText( myWorker.getActivity(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( myWorker.getActivity(), HomeActivity.class);
                    myWorker.getActivity().startActivity(intent);
                    myWorker.getActivity().finish();
                });
                myWorker.setSuccess(()->{
                    Log.e("Server return",myWorker.getResponse() );

                    try {
                        JSONArray jsonArray= new JSONArray(myWorker.getResponse());
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject accountJson=jsonArray.getJSONObject(i);
                            InfoShare infoShare= new InfoShare(accountJson.getString("Username"), accountJson.getString("Email"));
                            if(!shareNoteAdapter.getInfoShares().contains(infoShare))
                            shareNoteAdapter.getInfoShares().add(infoShare);
                        }
                        shareNoteAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e("JSONException", e.getMessage());
                    }
                });
                myWorker.setParams(new HashMap<String,String>(){{
                    put("usernameoremail",usernameOrEmail);
                }});

                PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "getusernameandemail.php");



            }
        }).start();
    }
}
