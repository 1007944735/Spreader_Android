package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class ValidateCodeModel {
    public String uuuid;
    public String validUrl;

    public ValidateCodeModel(JSONObject json) {
        uuuid = json.optString("uuuid");
        validUrl = json.optString("validUrl");
    }
}
