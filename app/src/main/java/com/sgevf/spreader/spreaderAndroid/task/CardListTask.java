package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.CardManagerActivity;
import com.sgevf.spreader.spreaderAndroid.model.CardListModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.CardService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class CardListTask extends BaseService<CardService, CardListModel> {
    public CardListTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.cardList(data);
    }

    @Override
    public void onSuccess(CardListModel cardListModel) {
        if(mTarget instanceof CardManagerActivity){
            ((CardManagerActivity) mTarget).onLoadFinish(cardListModel.list);
        }
    }
}
