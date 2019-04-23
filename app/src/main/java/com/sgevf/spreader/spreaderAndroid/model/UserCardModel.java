package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class UserCardModel {
    public int id;
    public String cardId;
    public int userId;
    public String isUse;
    public String getTime;
    public String endTime;
    public int redPacketId;

    public UserCardModel(JSONObject obj) {
        id = obj.optInt("id");
        cardId = obj.optString("cardId");
        userId = obj.optInt("userId");
        redPacketId = obj.optInt("redPacketId");
        isUse = obj.optString("isUse");
        getTime = obj.optString("getTime");
        endTime = obj.optString("endTime");
    }
}
