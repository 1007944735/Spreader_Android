package com.sgevf.spreader.http.api;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.base.impl.OnLoadingDialogListener;
import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.http.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleObserver<T> implements Observer<BasicResult<T>> {
    private static final String TAG="SimpleObserver";
    public ObserverOnNextListener<T> listener;
    private Activity mActivity;
    private boolean showLoading;
    public SimpleObserver(Activity mActivity) {
        this(mActivity,true);
    }
    public SimpleObserver(Activity mActivity,boolean show) {
        this.mActivity=mActivity;
        this.showLoading=show;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
        if(mActivity instanceof OnLoadingDialogListener&&showLoading){
            ((OnLoadingDialogListener) mActivity).show();
        }
    }

    @Override
    public void onNext(BasicResult<T> tBasicResult) {
        if("200".equals(tBasicResult.reCode)){
            listener.onNext(tBasicResult.params);
        }else if("-1".equals(tBasicResult.reCode)){
            ToastUtils.Toast(mActivity,tBasicResult.reInfo);
        }else if("400".equals(tBasicResult.reCode)){
            //登录
            ToastUtils.Toast(mActivity,tBasicResult.reInfo);
        }
    }


    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
        if(mActivity instanceof OnLoadingDialogListener&&showLoading){
            ((OnLoadingDialogListener) mActivity).dismiss();
        }
        ToastUtils.Toast(mActivity,e.getMessage());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: Over!");
        if(mActivity instanceof OnLoadingDialogListener){
            ((OnLoadingDialogListener) mActivity).dismiss();
        }
    }

    public ObserverOnNextListener<T> getListener() {
        return listener;
    }

    public void setListener(ObserverOnNextListener<T> listener) {
        this.listener = listener;
    }
}
