package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class WalletHistoryRedPacketDetailsModel {
    public Integer id;
    public String robMoney;
    public String robTime;
    public Integer redPacketId;
    public String amount;
    public String type;
    public String pubTime;
    public String pubLongitude;
    public String pubLatitude;
    public String startTime;
    public String endTime;
    public String maxNumber;
    public String pubAddress;
    public Integer userId;
    public Integer expandId;
    public String title;
    public String info;
    public String videoUrl;
    public String image1Url;
    public String image2Url;
    public String image3Url;
    public String image4Url;
    public String image5Url;
    public String image6Url;
    public String username;
    public String nickname;
    public String headPortrait;
    public String phone;

    public WalletHistoryRedPacketDetailsModel(JSONObject obj) {
        id = obj.optInt("id");
        robMoney = obj.optString("robMoney");
        robTime = obj.optString("robTime");
        redPacketId = obj.optInt("redPacketId");
        amount = obj.optString("amount");
        type = obj.optString("type");
        pubTime = obj.optString("pubTime");
        pubLongitude = obj.optString("pubLongitude");
        pubLatitude = obj.optString("pubLatitude");
        startTime = obj.optString("startTime");
        endTime = obj.optString("endTime");
        maxNumber = obj.optString("maxNumber");
        pubAddress = obj.optString("pubAddress");
        userId = obj.optInt("userId");
        expandId = obj.optInt("expandId");
        title = obj.optString("title");
        info = obj.optString("info");
        videoUrl = obj.optString("videoUrl");
        image1Url = obj.optString("image1Url");
        image2Url = obj.optString("image2Url");
        image3Url = obj.optString("image3Url");
        image4Url = obj.optString("image4Url");
        image5Url = obj.optString("image5Url");
        image6Url = obj.optString("image6Url");
        username = obj.optString("username");
        nickname = obj.optString("nickname");
        headPortrait = obj.optString("headPortrait");
        phone = obj.optString("phone");
    }
}
