package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.WalletHistoryDetailsActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistoryDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.AccountService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class WalletDetailedHistoryTask extends BaseService<AccountService, HistoryDetailsModel> {
    public WalletDetailedHistoryTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.queryHistory(data);
    }

    @Override
    public void onSuccess(HistoryDetailsModel historyDetailsModel) {
        if(mTarget instanceof WalletHistoryDetailsActivity){
            ((WalletHistoryDetailsActivity) mTarget).onLoadFinish(historyDetailsModel);
        }
    }
}
