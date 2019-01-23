package com.sgevf.spreader.http.api;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.sgevf.spreader.http.base.impl.OnLoadingDialogListener;
import com.sgevf.spreader.http.entity.BaseResult;
import com.sgevf.spreader.http.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleObserver<T> implements Observer<BaseResult<T>> {
    private static final String TAG="SimpleObserver";
    public ObserverOnNextListener<T> listener;
    private Activity mActivity;
    public SimpleObserver(Activity mActivity) {
        this.mActivity=mActivity;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
        if(mActivity instanceof OnLoadingDialogListener){
            ((OnLoadingDialogListener) mActivity).show();
        }
    }

    @Override
    public void onNext(BaseResult<T> tBaseResult) {
        listener.onNext(tBaseResult.data);
    }


    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
        if(mActivity instanceof OnLoadingDialogListener){
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
