package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class RedPacketDetailsModel {
    public Integer id;
    public Integer sponserId;
    public String sponserName;
    public String sponserImage;

    public Double amount;
    public String type;
    public String pubTime;
    public String pubLongitude;
    public String pubLatitude;
    public String startTime;
    public String endTime;
    public Integer maxNumber;
    public String pubAddress;
    public Double distance;
    public String title;
    public String info;
    public String isGrab;

    public RedPacketDetailsModel(JSONObject obj) {
        id = obj.optInt("id");
        sponserId = obj.optInt("sponserId");
        sponserName = obj.optString("sponserName");
        sponserImage = obj.optString("sponserImage");
        amount = obj.optDouble("amount");
        type = obj.optString("type");
        pubTime = obj.optString("pubTime");
        pubLongitude = obj.optString("pubLongitude");
        pubLatitude = obj.optString("pubLatitude");
        startTime = obj.optString("startTime");
        endTime = obj.optString("endTime");
        maxNumber = obj.optInt("maxNumber");
        pubAddress = obj.optString("pubAddress");
        distance = obj.optDouble("distance");
        title = obj.optString("title");
        info = obj.optString("info");
        isGrab = obj.optString("isGrab");
    }
}
