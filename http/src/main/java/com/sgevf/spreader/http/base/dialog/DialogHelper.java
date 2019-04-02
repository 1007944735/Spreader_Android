package com.sgevf.spreader.http.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

public class DialogHelper {
    public static Dialog initDefaultLoading(Context context) {
        return new DefaultLoading(context);
    }

    public static Dialog initLoading(Context context) {
        return new DialogLoading(context);
    }

    static class DefaultLoading extends Dialog {
        private Context context;

        public DefaultLoading(@NonNull Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ProgressBar bar = new ProgressBar(context);
            setContentView(bar);
        }
    }


}
