package com.example.mobile.webservice.ultils;

import org.json.JSONArray;
import org.json.JSONException;

public interface IRawSQL {
    public void acceptReturnRowAffect(int rowAffect);
    public void acceptReturnJSONArray(JSONArray jsonArray) throws JSONException;
}
