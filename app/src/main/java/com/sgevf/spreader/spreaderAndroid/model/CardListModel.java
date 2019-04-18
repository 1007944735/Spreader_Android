package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

import java.util.List;

public class CardListModel {

    public List<CardManagerModel> list;

    public class CardManagerModel {

        public int id;
        public String discountRule;
        public String useRule;
        public String startTime;
        public String effectiveTime;
        public String status;
        public String sellerName;

        public CardManagerModel(JSONObject obj) {
            id = obj.optInt("id");
            discountRule = obj.optString("discountRule");
            useRule = obj.optString("useRule");
            startTime = obj.optString("startTime");
            effectiveTime = obj.optString("effectiveTime");
            status = obj.optString("status");
            sellerName = obj.optString("sellerName");
        }
    }
}
