package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.WalletWithdrawActivity;
import com.sgevf.spreader.spreaderAndroid.model.CashWithdrawModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PayService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class WithdrawTask extends BaseService<PayService, CashWithdrawModel> {
    public WithdrawTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public WithdrawTask setClass(String count,String alipayAccount){
        params.put("count",count);
        params.put("alipayAccount",alipayAccount);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.withdraw(data);
    }

    @Override
    public void onSuccess(CashWithdrawModel model) {
        if(mTarget instanceof WalletWithdrawActivity){
            ((WalletWithdrawActivity) mTarget).onLoadFinish(model);
        }
    }
}
