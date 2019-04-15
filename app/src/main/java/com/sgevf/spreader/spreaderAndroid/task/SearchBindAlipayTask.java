package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.BindAlipayActivity;
import com.sgevf.spreader.spreaderAndroid.model.BindAlipayModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.AccountService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class SearchBindAlipayTask extends BaseService<AccountService, BindAlipayModel> {
    public SearchBindAlipayTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.searchIsBindAlipay(data);
    }

    @Override
    public void onSuccess(BindAlipayModel bindAlipayModel) {
        if (mTarget instanceof BindAlipayActivity){
            ((BindAlipayActivity) mTarget).onLoadFinish(bindAlipayModel);
        }
    }
}
