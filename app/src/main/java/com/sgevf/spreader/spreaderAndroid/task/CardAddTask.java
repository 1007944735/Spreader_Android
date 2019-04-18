package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.CardAddActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.CardService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class CardAddTask extends BaseService<CardService, String> {
    public CardAddTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public CardAddTask setClass(String discountRule, String useRule, String startTime, String effectiveTime) {
        params.put("discountRule", discountRule);
        params.put("useRule", useRule);
        params.put("startTime", startTime);
        params.put("effectiveTime", effectiveTime);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.addCard(data);
    }

    @Override
    public void onSuccess(String s) {
        if(mTarget instanceof CardAddActivity){
            ((CardAddActivity) mTarget).onLoadFinish(s);
        }
    }
}
