package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.model.ValidateCodeModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import org.json.JSONObject;

import io.reactivex.Observable;

public class ValidateCodeTask extends BasicApi<UserService, ValidateCodeModel> {

    public ValidateCodeTask(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected Observable setObservable(JSONObject json) {
        return service.code(json);
    }

    @Override
    public void onNext(ValidateCodeModel validateCodeModel) {
        if(mActivity instanceof RegisterActivity){
            ((RegisterActivity) mActivity).show(validateCodeModel);
        }
    }
}
