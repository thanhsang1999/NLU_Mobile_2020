package com.example.mobile.database.webservice;

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
import com.example.mobile.Config;
import com.example.mobile.activity.ForgotPassActivity;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.activity.LogInActivity;
import com.example.mobile.activity.SignUpActivity;
import com.example.mobile.database.sqlite.AccountDAO;
import com.example.mobile.database.sqlite.NoteDAO;
import com.example.mobile.database.sqlite.NoteSharedDAO;
import com.example.mobile.database.sqlite.PackageDAO;
import com.example.mobile.model.Account;
import com.example.mobile.model.MyImage;
import com.example.mobile.model.NoteShared;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;
import com.example.mobile.webservice.ultils.MyWorker;
import com.example.mobile.webservice.ultils.PrepareConnectionWebService;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConnectionWebService {
    protected Activity activity;
    protected AccountDAO accountDAO;
    private String addressHome = "https://mobilenlu2020.000webhostapp.com";
    private String urlQuery = "/home/query.php";
    public ConnectionWebService(Activity activity) {
        accountDAO = new AccountDAO(activity);
        this.activity = activity;
    }
    //quên mật khẩu
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

//                   SendMailSSL.sendMail(email, newpw);

                    return params;
                }


            };
            requestQueue.add(stringRequest);
        }
    }
    //đăng nhập
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
                    Log.e("Error", response.toString());
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

                            Account account = new Account(jsonObject.getInt("Id"),jsonObject.getString("Username"),
                                    jsonObject.getString("Fullname"), jsonObject.getString("Email"), jsonObject.getString("Password")
                            );
                            account.setDateOfBirth(Tool.StringToDate(jsonObject.getString("DateOfBirth")));
                            account.setGender(jsonObject.getString("Gender"));
                            String msg = "Đăng nhập thành công.";
                            logInActivity.loading_complete(null);
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            Log.e("Success", msg);
                            accountDAO.earse();
                            accountDAO.insert_account(account);

                            ConnectionWebService.this.takeData();
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

    //đăng ký
    public void insert_account(final Account account) {
        if (activity instanceof SignUpActivity) {

            final SignUpActivity signUpActivity = (SignUpActivity) activity;
            signUpActivity.loading(null);
            String url = Config.getURL() + "signup.php";


            final RequestQueue requestQueue = Volley.newRequestQueue(activity);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String msg = response.toString().trim();
                    Log.e("Return ", msg);
                    if (response.toString().trim().matches("[0-9]+")) {
                        msg = "Đăng ký thành công";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Success", msg);

                        signUpActivity.loading_complete(null);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        accountDAO.earse();
                        account.setId(Integer.parseInt(response.toString().trim()));
                        accountDAO.insert_account(account);
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
                        Log.e("Error", msg);
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
    
    //lấy data
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
    //
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
    public void takeData(){
        takeDataPackage();
        takeDataNotebook();
        takeDataMyNoteShared();
        //takeDataImage();



    }


    public void takeDataPackage(){
        PackageDAO packageDAO= new PackageDAO(ConnectionWebService.this.activity);
        MyWorker myWorker= new MyWorker();
        myWorker.setActivity(this.activity);
        myWorker.setError(()->{
            String msg = "Kết nối mạng bị lỗi.";
            Log.e("Error", myWorker.getErrorMessengr());

        });
        myWorker.setSuccess(()->{


            try {
                Log.e("Return", myWorker.getResponse());
                JSONArray jsonArray = new JSONArray(myWorker.getResponse());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Package p = new Package();
                    p.setId(jsonObject.getInt("Id"));
                    p.setColor(jsonObject.getString("Color"));
                    p.setName(jsonObject.getString("Title"));
                    p.setLastEdit(Tool.StringToDate(jsonObject.getString("LastEdit")));
                    packageDAO.insert_package(p,false);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException", e.getMessage());
            }
            packageDAO.close();



        });


        myWorker.setParams(new HashMap<String,String>(){{

            Account account= packageDAO.getAccount();
            put("username", account.getUsername());
        }});

        PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "getpackages.php");

    }
    public void takeDataNotebook(){
        NoteDAO packageDAO= new NoteDAO(ConnectionWebService.this.activity);
        MyWorker myWorker= new MyWorker();
        myWorker.setActivity(this.activity);
        myWorker.setError(()->{
            String msg = "Kết nối mạng bị lỗi.";
            Log.e("Error", myWorker.getErrorMessengr());

        });
        myWorker.setSuccess(()->{


            try {
                Log.e("Return", myWorker.getResponse());
                JSONArray jsonArray = new JSONArray(myWorker.getResponse());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Notebook p = new Notebook();
                    p.setId(jsonObject.getInt("Id"));
                    p.setTitle(jsonObject.getString("Title"));
                    p.setContent(jsonObject.getString("Content"));
                    p.setDateEdit(Tool.StringToDate(jsonObject.getString("LastEdit")));
                    p.id_package= jsonObject.getInt("IdPackage");
                    packageDAO.insertNotebook(p, p.id_package,false);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException", e.getMessage());
            }
            packageDAO.close();



        });


        myWorker.setParams(new HashMap<String,String>(){{

            Account account= packageDAO.getAccount();
            Log.e("Id", account.getId()+"");
            put("id_account", account.getId()+"");

        }});

        PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "getnotebooks.php");

    }
    public void takeDataMyNoteShared(){
        NoteSharedDAO packageDAO= new NoteSharedDAO(ConnectionWebService.this.activity);
        MyWorker myWorker= new MyWorker();
        myWorker.setActivity(this.activity);
        myWorker.setError(()->{
            String msg = "Kết nối mạng bị lỗi.";
            Log.e("Error", myWorker.getErrorMessengr());

        });
        myWorker.setSuccess(()->{


            try {
                Log.e("GetNoteshared", myWorker.getResponse());
                JSONArray jsonArray = new JSONArray(myWorker.getResponse());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    NoteShared p = new NoteShared();
                    p.setId(jsonObject.getInt("Id"));
                    p.setTitle(jsonObject.getString("Title"));
                    p.setContent(jsonObject.getString("Content"));
                    p.setDateEdit(Tool.StringToDate(jsonObject.getString("LastEdit")));
                    Account account= new Account(jsonObject.getInt("IdAccount"));
                    p.setAccount(account);
                    packageDAO.insertNoteShared(p,false);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException", e.getMessage());
            }
            packageDAO.close();



        });


        myWorker.setParams(new HashMap<String,String>(){{

            Account account= packageDAO.getAccount();
            Log.e("id", account.getId()+"");
            put("id", account.getId()+"");

        }});

        PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "getnoteshareds.php");

    }
    public void takeDataImage(){
        NoteDAO packageDAO= new NoteDAO(ConnectionWebService.this.activity);
        MyWorker myWorker= new MyWorker();
        myWorker.setActivity(this.activity);
        myWorker.setError(()->{
            String msg = "Kết nối mạng bị lỗi.";
            Log.e("Error", myWorker.getErrorMessengr());

        });
        myWorker.setSuccess(()->{


            try {
                Log.e("Return", myWorker.getResponse());
                JSONArray jsonArray = new JSONArray(myWorker.getResponse());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    MyImage p = new MyImage();
                    p.setId(jsonObject.getInt("Id"));
                    p.setImage(jsonObject.getString("Image"));

                    p.setLastEdit(Tool.StringToDate(jsonObject.getString("LastEdit")));
                    p.idNotebook=jsonObject.getInt("IdNotebook");
                    packageDAO.insertImage(p.idNotebook, p.getImage(),p.getLastEdit(),false);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException", e.getMessage());
            }
            packageDAO.close();



        });


        myWorker.setParams(new HashMap<String,String>(){{

            Account account= packageDAO.getAccount();
            put("username", account.getUsername());

        }});

        PrepareConnectionWebService.pushWebService(myWorker, Config.getURL()+ "getnotebooks.php");

    }

    public void loginOutside(String outside, Account account) {
        String url = Config.getURL() + "loginoutside.php";



        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Return", response.toString());
                    JSONArray jsonArray = new JSONArray(response.toString());
                    if (jsonArray.length() > 1) {
                        String msg = "Tài khoản không hợp lệ.";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Error", msg);
                        return;
                    }
                    if (jsonArray.length() == 0) {
                        String msg = "Đăng ký tài khoản mới với outside.";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("loginOutside", msg);

                        //
                        signUpOutside(outside, account);
                        return;


                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Account account = new Account(jsonObject.getInt("Id"),jsonObject.getString("Username"),
                                jsonObject.getString("Fullname"), jsonObject.getString("Email"), jsonObject.getString("Password")
                        );
                        account.setOutside(jsonObject.getString("Outside"));
                        account.setIdOutSide(jsonObject.getString("IdOutside"));
                        account.setDateOfBirth(Tool.StringToDateShort(jsonObject.getString("DateOfBirth")));
                        account.setGender(jsonObject.getString("Gender"));

                        String msg = "Đăng nhập thành công.";

                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Success", msg);
                        accountDAO.earse();
                        accountDAO.insert_account(account);

                        ConnectionWebService.this.takeData();
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

                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_outside", Profile.getCurrentProfile().getId());
                params.put("outside", outside);
                return params;
            }


        };
        requestQueue.add(jsonArrayRequest);
    }
    //đăng ký
    public void signUpOutside(String outside, Account account) {
        if (activity instanceof LogInActivity) {

            final LogInActivity signUpActivity = (LogInActivity) activity;
            signUpActivity.loading(null);
            String url = Config.getURL() + "signupoutside.php";


            final RequestQueue requestQueue = Volley.newRequestQueue(activity);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String msg = response.toString().trim();
                    Log.e("Return", msg);
                    if (msg.matches("[0-9]+")) {
                        msg = "Đăng ký thành công";
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        Log.e("Success", msg);

                        signUpActivity.loading_complete(null);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        accountDAO.earse();
                        account.setId(Integer.parseInt(msg));
                        accountDAO.insert_account(account);
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
                    params.put("fullname", account.getFullname());
                    params.put("email", account.getEmail());
                    params.put("outside", outside);
                    params.put("id_outside", account.getIdOutSide());
                    Log.e("Gender", account.getGender());
                    params.put("gender", account.getGender());
                    Log.e("dateofbirth", Tool.DateToString(account.getDateOfBirth()));
                    params.put("dateofbirth", Tool.DateToString(account.getDateOfBirth()));

                    return params;
                }


            };
            requestQueue.add(stringRequest);
        }

    }
}
