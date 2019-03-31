package com.sgevf.spreader.http.api;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.base.impl.OnLoadingDialogListener;
import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.http.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleObserver<T> implements Observer<BasicResult<T>> {
    private static final String TAG = "SimpleObserver";
    public ObserverListener<T> listener;
    private Activity mActivity;
    private Object mTarget;
    private boolean showLoading;

    public SimpleObserver(Activity mActivity, Object mTarget) {
        this(mActivity, mTarget, true);
    }

    public SimpleObserver(Activity mActivity, Object mTarget, boolean show) {
        this.mActivity = mActivity;
        this.mTarget = mTarget;
        this.showLoading = show;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
        if (mTarget instanceof OnLoadingDialogListener && showLoading) {
            ((OnLoadingDialogListener) mTarget).show();
        }
        this.listener.beforeRequest(d);
    }

    @Override
    public void onNext(BasicResult<T> tBasicResult) {
        if ("200".equals(tBasicResult.reCode)) {
            listener.onSuccess(tBasicResult.params);
        } else {
            this.listener.onFail(tBasicResult.reCode);
            ToastUtils.Toast(mActivity, tBasicResult.reInfo);
        }
    }


    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
        if (mTarget instanceof OnLoadingDialogListener && showLoading) {
            ((OnLoadingDialogListener) mTarget).dismiss();
        }
        this.listener.onError(e);
        ToastUtils.Toast(mActivity, e.getMessage());
    }

    @Override
    public void onComplete() {
        if (mTarget instanceof OnLoadingDialogListener) {
            ((OnLoadingDialogListener) mTarget).dismiss();
        }
        this.listener.finishRequest();
    }

    public ObserverListener<T> getListener() {
        return listener;
    }

    public void setListener(ObserverListener<T> listener) {
        this.listener = listener;
    }
}
