package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class UserAccountModel {
    public Integer id;
    public String balance;
    public String alipayAccount;

    public UserAccountModel(JSONObject obj){
        id=obj.optInt("id");
        balance=obj.optString("balance");
        alipayAccount=obj.optString("alipay_account");
    }

}
