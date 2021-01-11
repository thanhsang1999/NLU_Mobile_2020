package com.example.mobile;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.LogInActivity;
import com.example.mobile.activity.LogoActivity;
import com.example.mobile.activity.SignUpActivity;
import com.example.mobile.activity.WellComeActivity;
import com.example.mobile.model.Account;
import com.example.mobile.model.Package;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConnectionWebService {
    private Activity activity;
    private ConnectionDatabaseLocalMobile connectionDatabaseLocalMobile;
    private String addressHome = "https://mobilenlu2020.000webhostapp.com";
    private String urlQuery = "/home/query.php";
    public ConnectionWebService(Activity activity) {
        connectionDatabaseLocalMobile= new ConnectionDatabaseLocalMobile(activity);
        this.activity = activity;
    }

    public void forgotPassword(final String email) {

        if (activity instanceof ForgotPassActivity) {
            final ForgotPassActivity forgotActivity = (ForgotPassActivity) activity;
            forgotActivity.loading(null);

            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            String url = Config.getURL() + "forgotpw.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String msg = "";
                    if (response.toString().trim().equals("OK")) {
                        msg = "Vui lòng check email";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Succuss", msg);

                        forgotActivity.loading_complete(null);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LogInActivity.class);

                        activity.startActivity(intent);

                    } else if (response.toString().trim().equals("Error")) {
                        msg = "Không thành công, vui lòng thử lại";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Error", msg);

                        Log.e("Error", msg.toString());
                        forgotActivity.loading_complete(null);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String msg = "Kết nối mạng bị lỗi.";
                    Log.e("Error", error.toString());
                    forgotActivity.loading_complete(null);
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String newpw = randomPW();
                    Map<String, String> params = new HashMap<>();
                    params.put("newpw", newpw);
                    params.put("email", email);

//                    SendMailSSL.sendMail(email, newpw);

                    return params;
                }


            };
            requestQueue.add(stringRequest);
        }
    }

    public void login(final String username, final String password) {
        if (activity instanceof LogInActivity) {
            final LogInActivity logInActivity = (LogInActivity) activity;
            logInActivity.loading(null);
            String url = Config.getURL() + "login.php";


            RequestQueue requestQueue = Volley.newRequestQueue(activity);

            StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
//                    Log.e("Error", response.toString());
                        JSONArray jsonArray = new JSONArray(response.toString());
                        if (jsonArray.length() != 1) {
                            String msg = "Tài khoản không hợp lệ.";
                            logInActivity.loading_complete(null);
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            Log.e("Error", msg);
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Account account = new Account(jsonObject.getString("Username"),
                                    jsonObject.getString("Fullname"), jsonObject.getString("Email"), jsonObject.getString("Password")
                            );
                            String msg = "Đăng nhập thành công.";
                            logInActivity.loading_complete(null);
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            Log.e("Success", msg);
                            connectionDatabaseLocalMobile.earse();
                            connectionDatabaseLocalMobile.insert_account(account);
                            Intent intent = new Intent(activity, HomeActivity.class);

                            activity.startActivity(intent);
                            activity.finish();





                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String msg = "Kết nối mạng bị lỗi.";
                    Log.e("Error", error.toString());
                    logInActivity.loading_complete(null);
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                }
            }) {

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


    }
    public void alive() {
        if (activity instanceof LogoActivity) {

            String url = Config.getURL() + "alive.php";


            RequestQueue requestQueue = Volley.newRequestQueue(activity);

            StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.toString().equals("alive")){
                        Log.e("Connection", "OK");
                        Intent intent = new Intent(activity, WellComeActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String msg = "Kết nối mạng bị lỗi.";
                    Log.e("Error", error.toString());

                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, HomeActivity.class);
                    activity.startActivity(intent);
                    activity.finish();


                }
            });
            requestQueue.add(jsonArrayRequest);
        }


    }

    public void insert_account(final Account account) {
        if (activity instanceof SignUpActivity) {

            final SignUpActivity signUpActivity = (SignUpActivity) activity;
            signUpActivity.loading(null);
            String url = Config.getURL() + "signup.php";


            final RequestQueue requestQueue = Volley.newRequestQueue(activity);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String msg = "";
                    if (response.toString().trim().equals("OK")) {
                        msg = "Đăng ký thành công";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Success", msg);

                        signUpActivity.loading_complete(null);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        connectionDatabaseLocalMobile.earse();
                        connectionDatabaseLocalMobile.insert_account(account);
                        Intent intent = new Intent(activity, HomeActivity.class);

                        activity.startActivity(intent);
                        activity.finish();

                    } else if (response.toString().trim().equals("Error")) {
                        msg = "Đăng ký không thành công";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Error", msg.toString());
                        signUpActivity.loading_complete(null);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                    } else{
                        msg = "Database bị lỗi.";
                        Log.e("Error", response.toString());
                        signUpActivity.loading_complete(null);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String msg = "Kết nối mạng bị lỗi.";
                    Log.e("Error", error.toString());
                    signUpActivity.loading_complete(null);
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                }
            }) {

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

    }
    public void insert_package(Package p, Account account){

        if(account==null)return;
        String url = Config.getURL() + "addpackage.php";



        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String msg = "";
                if (response.toString().trim().equals("OK")) {
                    msg = "Gửi package lên webservice thành công";
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    Log.e("Success", msg);


                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, HomeActivity.class);


                } else if (response.toString().trim().equals("Error")) {
                    msg = "Gửi package lên webservice không thành công";
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();


                    Log.e("Error", msg.toString());

                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                } else{
                    msg = "Database bị lỗi";



                    Log.e("Error", msg.toString());
                    Log.e("PHP Return", response.toString());

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String msg = "Kết nối mạng bị lỗi.";
                Log.e("Error", error.toString());

                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", p.getId()+"");
                params.put("color", p.getColor()+"");
                params.put("title", p.getName());
                Log.e("Date", p.getLastEdit().getText());
                params.put("last_edit", p.getLastEdit().getText());
                params.put("username", account.getUsername());
                return params;
            }


        };
        requestQueue.add(stringRequest);

    }

    private void getdata(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
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


    //random password
    public static String randomPW() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

//        System.out.println(generatedString);
        return generatedString;
    }
    public boolean QuerySQL(String string){
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, addressHome+urlQuery, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                }else {
                    Toast.makeText(activity, ""+response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("query",string);
                return map;
            }
        };
        requestQueue.add(stringRequest);
        return false;

    }

}
