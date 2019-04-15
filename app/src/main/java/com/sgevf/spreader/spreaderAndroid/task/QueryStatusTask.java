package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.WalletHistoryWithdrawDetailsActivity;
import com.sgevf.spreader.spreaderAndroid.model.CashWithdrawModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PayService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class QueryStatusTask extends BaseService<PayService, CashWithdrawModel> {
    public QueryStatusTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public QueryStatusTask setClass(String outBizNo){
        params.put("outBizNo",outBizNo);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.queryStatus(data);
    }

    @Override
    public void onSuccess(CashWithdrawModel cashWithdrawModel) {
        if(mTarget instanceof WalletHistoryWithdrawDetailsActivity){
            ((WalletHistoryWithdrawDetailsActivity) mTarget).queryResult(cashWithdrawModel);
        }
    }
}
