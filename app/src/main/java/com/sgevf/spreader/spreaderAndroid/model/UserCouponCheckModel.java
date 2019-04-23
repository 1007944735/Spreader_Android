package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class UserCouponCheckModel {
    public int type;
    public int cardId;
    public String discountRule;
    public String useRule;
    public String status;
    public String getTime;
    public String endTime;

    public UserCouponCheckModel(JSONObject obj) {
        type = obj.optInt("type");
        cardId = obj.optInt("cardId");
        discountRule = obj.optString("discountRule");
        useRule = obj.optString("useRule");
        status = obj.optString("status");
        getTime = obj.optString("getTime");
        endTime = obj.optString("endTime");
    }
}
