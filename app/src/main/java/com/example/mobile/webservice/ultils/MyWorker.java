package com.example.mobile.webservice.ultils;

import android.app.Activity;

import org.json.JSONArray;

import java.util.Map;

public class MyWorker{
    private Map<String,String> params;
    private String response;
    private String errorMessengr;
    private Runnable success;
    private Runnable error;
    private Activity activity;


    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }



    public Runnable getSuccess() {
        return success;
    }

    public void setSuccess(Runnable success) {
        this.success = success;
    }

    public Runnable getError() {
        return error;
    }

    public void setError(Runnable error) {
        this.error = error;
    }

    public String getErrorMessengr() {
        return errorMessengr;
    }

    public void setErrorMessengr(String errorMessengr) {
        this.errorMessengr = errorMessengr;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
