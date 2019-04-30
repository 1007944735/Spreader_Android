package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.fragment.HomeFragment;
import com.sgevf.spreader.spreaderAndroid.model.HomeAdvertisingListModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class HomeAdvertisingListTask extends BaseService<Service, HomeAdvertisingListModel> {
    public HomeAdvertisingListTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.getHomeList();
    }

    @Override
    public void onSuccess(HomeAdvertisingListModel homeAdvertisingListModel) {
        if (mTarget instanceof HomeFragment) {
            ((HomeFragment) mTarget).onLoadFinish(homeAdvertisingListModel);
        }
    }

    @Override
    protected boolean setShowLoading() {
        return false;
    }
}
