package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class HistorySurplusModel {
    public String surplus;

    public HistorySurplusModel(JSONObject obj) {
        surplus = obj.optString("surplus");
    }
}
