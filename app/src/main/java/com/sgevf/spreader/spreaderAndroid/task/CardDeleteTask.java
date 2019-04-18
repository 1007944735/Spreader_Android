package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.CardManagerActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.CardService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class CardDeleteTask extends BaseService<CardService,String> {
    public CardDeleteTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public CardDeleteTask setClass(int id){
        params.put("id",id+"");
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.deleteCard(data);
    }

    @Override
    public void onSuccess(String s) {
        if(mTarget instanceof CardManagerActivity){
            ((CardManagerActivity) mTarget).deleteSuccess(s);
        }
    }
}
