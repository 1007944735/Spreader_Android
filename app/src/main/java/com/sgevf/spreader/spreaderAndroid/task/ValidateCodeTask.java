package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.model.ValidateCodeModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import java.util.Map;

import io.reactivex.Observable;

public class ValidateCodeTask extends BaseService<Service, ValidateCodeModel> {

    public ValidateCodeTask(Activity mActivity) {
        super(mActivity);
    }


    @Override
    protected Observable setObservable(Map data) {
        return service.getValid(data);
    }

    @Override
    public void onNext(ValidateCodeModel validateCodeModel) {
        if (mActivity instanceof RegisterActivity) {
            ((RegisterActivity) mActivity).show(validateCodeModel);
        }
    }
}
