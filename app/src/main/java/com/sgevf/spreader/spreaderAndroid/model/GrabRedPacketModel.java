package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class GrabRedPacketModel {
    public String money;
    public String name;
    public GrabRedPacketModel(JSONObject obj){
        money=obj.optString("money");
        name=obj.optString("name");
    }
}
