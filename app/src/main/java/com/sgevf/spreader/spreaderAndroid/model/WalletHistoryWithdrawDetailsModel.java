package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class WalletHistoryWithdrawDetailsModel {
    public Integer id;
    public Integer takerId;
    public String money;
    public String time;
    public String way;
    public String status;
    public String failReason;

    public WalletHistoryWithdrawDetailsModel(JSONObject obj) {
        id = obj.optInt("id");
        takerId = obj.optInt("takerId");
        money = obj.optString("money");
        time = obj.optString("time");
        way = obj.optString("way");
        status = obj.optString("status");
        failReason = obj.optString("failReason");
    }
}
