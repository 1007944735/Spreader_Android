package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.BusinessInfoActivity;
import com.sgevf.spreader.spreaderAndroid.model.BusinessModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.BusinessService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class SearchBusinessInfoTask extends BaseService<BusinessService,BusinessModel> {
    public SearchBusinessInfoTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.searchBusinessInfo(data);
    }

    @Override
    public void onSuccess(BusinessModel businessModel) {
        if(mTarget instanceof BusinessInfoActivity){
            ((BusinessInfoActivity) mTarget).onLoadFinish(businessModel);
        }
    }
}
