package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.UserCardActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponListModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserCouponService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UserCouponTask extends BaseService<UserCouponService, UserCouponListModel> {
    public UserCouponTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.getUserCoupon(data);
    }

    @Override
    public void onSuccess(UserCouponListModel userCouponListModel) {
        if (mTarget instanceof UserCardActivity) {
            ((UserCardActivity) mTarget).onLoadFinish(userCouponListModel);
        }
    }
}
