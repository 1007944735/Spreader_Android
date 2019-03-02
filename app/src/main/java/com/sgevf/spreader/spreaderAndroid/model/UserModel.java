package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class UserModel {
    public Integer userId;
    public String nickName;
    public String userName;
    public String password;
    public String userHead;
    public String userPhone;

    public UserModel(JSONObject obj) {
        userId = obj.optInt("userId");
        nickName = obj.optString("nickName");
        userName = obj.optString("userName");
        password = obj.optString("password");
        userHead = obj.optString("userHead");
        userPhone = obj.optString("userPhone");
    }
}
