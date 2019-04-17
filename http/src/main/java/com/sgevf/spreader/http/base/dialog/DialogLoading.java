package com.sgevf.spreader.http.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

public class DialogLoading extends Dialog {
    private View view;

    public DialogLoading(@NonNull Context context, View view) {
        super(context);
        this.view=view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
    }
}
