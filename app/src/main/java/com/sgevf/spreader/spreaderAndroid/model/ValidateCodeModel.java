package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class ValidateCodeModel {
    public String uuid;
    public String url;

    public ValidateCodeModel(JSONObject json){
        uuid=json.optString("uuid");
        url=json.optString("url");
    }
}
