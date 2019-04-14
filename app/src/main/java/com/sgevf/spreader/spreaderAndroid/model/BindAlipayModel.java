package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class BindAlipayModel {
    public int id;
    public String isGrant;
    public String alipayUid;
    public String alipayHead;
    public String alipayName;
    public String balance;

    public BindAlipayModel(JSONObject obj) {
        id = obj.optInt("id");
        isGrant = obj.optString("isGrant");
        alipayUid = obj.optString("alipayUid");
        alipayHead = obj.optString("alipayHead");
        alipayName = obj.optString("alipayName");
        balance = obj.optString("balance");
    }
}
