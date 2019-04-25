package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.BusinessAuthActivity;
import com.sgevf.spreader.spreaderAndroid.model.BusinessModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.BusinessService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UploadBusinessInfoTask extends BaseService<BusinessService, BusinessModel> {
    public UploadBusinessInfoTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public UploadBusinessInfoTask setClass(String bName, String bLicense, String bLogo, String bIdcardFront, String bIdcardBack, String bAddress, String bSocialCredit, String bPhone, String bContent) {
        params.put("bName", bName);
        params.put("bLicense", bLicense);
        params.put("bLogo", bLogo);
        params.put("bIdcardFront", bIdcardFront);
        params.put("bIdcardBack", bIdcardBack);
        params.put("bAddress", bAddress);
        params.put("bSocialCredit", bSocialCredit);
        params.put("bPhone", bPhone);
        params.put("bContent", bContent);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.uploadInfo(data);
    }

    @Override
    public void onSuccess(BusinessModel businessModel) {
        if(mTarget instanceof BusinessAuthActivity){
            ((BusinessAuthActivity) mTarget).onLoadFinish(businessModel);
        }
    }
}
