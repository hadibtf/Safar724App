package com.safar724test.app.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomJsonParser {
    private final String obj;
    private final JSONObject jsonObject;

    public CustomJsonParser(String obj) throws JSONException {
        this.obj = obj;
        this.jsonObject = new JSONObject(obj);
    }

    public JSONArray getJsonArray(String jsonArray) {
        try {
            return new JSONArray(jsonObject.getJSONArray(jsonArray));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getObj(String jsonObj) throws JSONException {
        return jsonObject.getString(jsonObj);
    }
}
