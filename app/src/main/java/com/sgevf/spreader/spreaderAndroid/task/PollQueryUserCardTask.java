package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.QRCodeCouponActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserCardModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserCouponService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class PollQueryUserCardTask extends BaseService<UserCouponService, UserCardModel> {
    public PollQueryUserCardTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public PollQueryUserCardTask setClass(int userCardId) {
        params.put("userCardId", userCardId + "");
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.queryUserCard(data);
    }

    @Override
    public void onSuccess(UserCardModel userCardModel) {
        if(mTarget instanceof QRCodeCouponActivity){
            ((QRCodeCouponActivity) mTarget).onLoadFinish(userCardModel);
        }
    }

    @Override
    protected boolean setShowLoading() {
        return false;
    }
}
