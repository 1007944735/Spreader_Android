package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.QrCodeCameraActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponCheckModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserCouponService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UserCouponCheckTask extends BaseService<UserCouponService, UserCouponCheckModel> {
    public UserCouponCheckTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public UserCouponCheckTask setClass(int couponId, int redPacketId) {
        params.put("couponId", couponId + "");
        params.put("redPacketId", redPacketId + "");
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.checkUserCoupon(data);
    }

    @Override
    public void onSuccess(UserCouponCheckModel userCouponCheckModel) {
        if (mTarget instanceof QrCodeCameraActivity) {
            ((QrCodeCameraActivity) mTarget).onLoadFinish(userCouponCheckModel);
        }
    }
}
