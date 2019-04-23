package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.HomeActivity;
import com.sgevf.spreader.spreaderAndroid.model.SlideShowModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class HomeSlideShowTask extends BaseService<Service, SlideShowModel> {
    public HomeSlideShowTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.getSlideShow(data);
    }

    @Override
    public void onSuccess(SlideShowModel slideShowModel) {
        if (mTarget instanceof HomeActivity) {
            ((HomeActivity) mTarget).onLoadFinish(slideShowModel);
        }
    }

    @Override
    protected boolean setShowLoading() {
        return false;
    }
}
