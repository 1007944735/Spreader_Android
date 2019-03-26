package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class UserModel {
    public Integer id;
    public String nickname;
    public String username;
    public String headPortrait;
    public String phone;
    public String token;

    public UserModel(JSONObject obj) {
        id = obj.optInt("id");
        nickname = obj.optString("nickname");
        username = obj.optString("username");
        headPortrait = obj.optString("headPortrait");
        phone = obj.optString("phone");
        token = obj.optString("token");
    }
}
