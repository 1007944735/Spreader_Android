package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.model.ValidateCodeModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class ValidateCodeTask extends BaseService<Service, ValidateCodeModel> {


    public ValidateCodeTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public void onNext(ValidateCodeModel validateCodeModel) {
        if (mTarget instanceof RegisterActivity) {
            ((RegisterActivity) mTarget).show(validateCodeModel);
        }
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.getValid();
    }
}
