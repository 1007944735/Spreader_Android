package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

import java.util.List;

public class GrabRedPacketModel {
    public String money;
    public String name;
    public List<CardListModel.CardManagerModel> list;

    public GrabRedPacketModel(JSONObject obj) {
        money = obj.optString("money");
        name = obj.optString("name");
    }
}
