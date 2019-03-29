package com.sgevf.spreader.http.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.sgevf.spreader.http.base.dialog.DialogHelper;
import com.sgevf.spreader.http.base.impl.OnLoadingDialogListener;

public abstract class BasicLoadingFragment<T> extends Fragment implements OnLoadingDialogListener<T> {
    protected Dialog loading;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefaultLoading();
    }

    private void initDefaultLoading() {
        loading = DialogHelper.initDefaultLoading(context);
    }

    @Override
    public void show() {
        if (loading != null) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            loading.show();
        }
    }

    @Override
    public void dismiss() {
        if (loading != null) {
            loading.dismiss();
        }
    }
}
