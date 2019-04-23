package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.QrCodeCameraActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserCouponService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UseCouponTask extends BaseService<UserCouponService, String> {
    public UseCouponTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public UseCouponTask setClass(int userCardId) {
        params.put("userCardId", userCardId + "");
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.useCoupon(data);
    }

    @Override
    public void onSuccess(String s) {
        if (mTarget instanceof QrCodeCameraActivity) {
            ((QrCodeCameraActivity) mTarget).useFinish(s);
        }
    }
}
