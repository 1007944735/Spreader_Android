package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class AliAuthInfoModel {
    public String url;

    public AliAuthInfoModel(JSONObject obj) {
        url = obj.optString("url");
    }
}
