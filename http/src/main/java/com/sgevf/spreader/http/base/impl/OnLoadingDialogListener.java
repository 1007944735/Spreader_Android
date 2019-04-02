package com.sgevf.spreader.http.base.impl;

public interface OnLoadingDialogListener<T> {
    void show();

    void dismiss();

    void onLoadFinish(T t);
}
