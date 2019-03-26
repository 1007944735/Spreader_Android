package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class InitModel {
    public String publicKey;

    public InitModel(JSONObject obj) {
        this.publicKey =obj.optString("publicKey");
    }
}
