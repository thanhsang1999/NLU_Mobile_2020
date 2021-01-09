package com.example.mobile.webservice.ultils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class PrepareConnectionWebService {
    public static String pushWebService(final MyWorker myWorker, String url){
        String rs=null;
        RequestQueue requestQueue = Volley.newRequestQueue(myWorker.getActivity());
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    myWorker.setResponse(response.toString());
                    myWorker.getSuccess().run();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("PrepareWebservice", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myWorker.setErrorMessengr(error.toString());
                myWorker.getError().run();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return myWorker.getParams();
            }


        };
        requestQueue.add(jsonArrayRequest);
        return rs;
    }

}
