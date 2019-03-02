package com.sgevf.spreader.http.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class JsonUtils {
    public static JSONObject createJson(Map<String,Object> params){
        JSONObject json=new JSONObject();
        try {
            json.putOpt("T","debug");
            json.putOpt("values",new JSONObject(params));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
