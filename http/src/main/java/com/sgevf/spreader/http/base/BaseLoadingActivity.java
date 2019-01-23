package com.sgevf.spreader.http.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sgevf.spreader.http.base.dialog.DialogHelper;
import com.sgevf.spreader.http.base.impl.OnLoadingDialogListener;

public abstract class BaseLoadingActivity<T> extends BaseActivity implements OnLoadingDialogListener<T>{
    protected Dialog loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefaultLoading();
    }

    private void initDefaultLoading() {
        loading= DialogHelper.initDefaultLoading(this);
    }

    @Override
    public void show() {
        if(loading!=null){
            if(loading.isShowing()){
                loading.dismiss();
            }
            loading.show();
        }
    }

    @Override
    public void dismiss() {
        if(loading!=null){
            loading.dismiss();
        }
    }
}
