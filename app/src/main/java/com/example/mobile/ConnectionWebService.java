package com.example.mobile;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConnectionWebService {
    private Activity activity;

    public ConnectionWebService(Activity activity) {
        this.activity = activity;
    }

    public void login(final String username, final String password){
        String url=Config.getURL()+"login.php";



        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
//                    Log.e("Error", response.toString());
                    JSONArray jsonArray = new JSONArray(response.toString());
                    if(jsonArray.length()!=1){
                        String msg="Tài khoản không hợp lệ.";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Error", msg);
                        return ;
                    }
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Account account= new Account(jsonObject.getString("Username"),
                                jsonObject.getString("Fullname"),jsonObject.getString("Email"),jsonObject.getString("Password")
                                );
                        String msg="Đăng nhập thành công.";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Success", msg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", error.toString());

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }


        };
        requestQueue.add(jsonArrayRequest);



    }
    public void insert_accounts(final Account account){
        String url=Config.getURL()+"signup.php";



        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String msg="";
                if(response.toString().trim().equals("OK")){
                    msg="Đăng ký thành công";
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    Log.e("Succuss", msg);

                }
                else   if(response.toString().trim().equals("Error")){
                    msg="Đăng ký không thành công";
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    Log.e("Error", msg);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", error.toString());

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", account.getUsername());
                params.put("password", account.getPassword());
                params.put("fullname", account.getFullname());
                params.put("email", account.getEmail());

                return params;
            }


        };
        requestQueue.add(stringRequest);

    }
    private void getdata(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
}
