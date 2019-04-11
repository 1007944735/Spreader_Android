package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class PubResultModel {
    public String id;
    public String amount;

    public PubResultModel(JSONObject obj) {
        id = obj.optString("id");
        amount = obj.optString("amount");
    }
}
