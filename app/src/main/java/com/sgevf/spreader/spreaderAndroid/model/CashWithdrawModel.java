package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class CashWithdrawModel {
    public String orderId;
    public String status;
    public String payDate;
    public String failReason;
    public String outBizNo;
    public String errorCode;
    public int withdrawId;

    public CashWithdrawModel(JSONObject obj) {
        orderId = obj.optString("orderId");
        status = obj.optString("status");
        payDate = obj.optString("payDate");
        failReason = obj.optString("failReason");
        outBizNo = obj.optString("outBizNo");
        errorCode = obj.optString("errorCode");
        withdrawId = obj.optInt("withdrawId");
    }
}
