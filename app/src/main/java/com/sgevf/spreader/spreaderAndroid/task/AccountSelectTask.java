package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.WalletActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserAccountModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.AccountService;

import java.util.Map;
import java.util.ServiceConfigurationError;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class AccountSelectTask extends BaseService<AccountService, UserAccountModel> {
    public AccountSelectTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.selectAccount(data);
    }

    @Override
    public void onSuccess(UserAccountModel userAccountModel) {
        if(mTarget instanceof WalletActivity){
            ((WalletActivity) mTarget).onLoadFinish(userAccountModel);
        }
    }
}
