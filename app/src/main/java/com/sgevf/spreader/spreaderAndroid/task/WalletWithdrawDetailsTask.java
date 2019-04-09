package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.WalletHistoryWithdrawDetailsActivity;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryWithdrawDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.AccountService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class WalletWithdrawDetailsTask extends BaseService<AccountService, WalletHistoryWithdrawDetailsModel> {
    public WalletWithdrawDetailsTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public WalletWithdrawDetailsTask setClass(int id) {
        params.put("id", id + "");
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.withdrawDetails(data);
    }

    @Override
    public void onSuccess(WalletHistoryWithdrawDetailsModel model) {
        if (mTarget instanceof WalletHistoryWithdrawDetailsActivity) {
            ((WalletHistoryWithdrawDetailsActivity) mTarget).onLoadFinish(model);
        }
    }
}
