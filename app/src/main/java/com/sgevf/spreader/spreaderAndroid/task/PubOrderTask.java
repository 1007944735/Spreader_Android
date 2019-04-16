package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.PayActivity;
import com.sgevf.spreader.spreaderAndroid.model.PubOrderModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PayService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class PubOrderTask extends BaseService<PayService, PubOrderModel> {
    public PubOrderTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public PubOrderTask setClass(String amount,String redPacketId,String order){
        params.put("amount",amount);
        params.put("redPacketId",redPacketId);
        params.put("order",order);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.pubOrder(data);
    }

    @Override
    public void onSuccess(PubOrderModel pubOrderModel) {
        if(mTarget instanceof PayActivity){
            ((PayActivity) mTarget).onLoadFinish(pubOrderModel);
        }
    }
}
