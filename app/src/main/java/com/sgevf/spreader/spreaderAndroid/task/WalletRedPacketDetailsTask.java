package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.WalletHistoryRedPacketDetailsActivity;
import com.sgevf.spreader.spreaderAndroid.activity.WalletHistoryWithdrawDetailsActivity;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryRedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryWithdrawDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.AccountService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class WalletRedPacketDetailsTask extends BaseService<AccountService, WalletHistoryRedPacketDetailsModel> {
    public WalletRedPacketDetailsTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public WalletRedPacketDetailsTask setClass(int id) {
        params.put("id", id + "");
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.redPacketDetails(data);
    }

    @Override
    public void onSuccess(WalletHistoryRedPacketDetailsModel model) {
        if (mTarget instanceof WalletHistoryRedPacketDetailsActivity) {
            ((WalletHistoryRedPacketDetailsActivity) mTarget).onLoadFinish(model);
        }
    }
}
