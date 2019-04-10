package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class PubOrderModel {
    public int id;
    public String orderString;
    public String amount;

    public PubOrderModel(JSONObject obj) {
        id = obj.optInt("id");
        orderString = obj.optString("orderString");
        amount = obj.optString("amount");
    }
}
