package com.sgevf.spreader.http.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class JsonUtils {
    public static JSONObject createJson(Map<String, Object> params) {
        JSONObject json = new JSONObject();
        try {
            for (String key : params.keySet()) {
                json.putOpt(key, params.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
