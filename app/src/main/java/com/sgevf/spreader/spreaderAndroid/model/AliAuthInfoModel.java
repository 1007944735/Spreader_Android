package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class AliAuthInfoModel {
    public String url;
    public String authInfo;

    public AliAuthInfoModel(JSONObject obj) {
        authInfo = obj.optString("authInfo");
        url = obj.optString("url");
    }
}
