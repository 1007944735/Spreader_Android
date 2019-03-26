package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.model.ValidateCodeModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;

public class ValidateCodeTask extends BasicApi<Service, ValidateCodeModel> {

    public ValidateCodeTask(Activity mActivity) {
        super(mActivity);
    }


    @Override
    protected Observable setObservable(Map data) {
        return super.setObservable(data);
    }

    @Override
    public void onNext(ValidateCodeModel validateCodeModel) {
        if (mActivity instanceof RegisterActivity) {
            ((RegisterActivity) mActivity).show(validateCodeModel);
        }
    }
}
