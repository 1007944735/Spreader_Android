package com.sgevf.spreader.spreaderAndroid.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.HomeActivity;

import utils.WindowHelper;

public class HeaderView implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView title;
    private TextView right;
    private ImageView rightIcon;
    private Activity activity;

    public HeaderView(Activity activity) {
        this.activity = activity;
        toolbar = activity.findViewById(R.id.toolbar);
        title = activity.findViewById(R.id.title);
        right = activity.findViewById(R.id.right);
        rightIcon = activity.findViewById(R.id.rightIcon);
//        WindowHelper.setViewPaddingTop(activity, toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    public HeaderView setRight(String str) {
        setRight(str, null);
        return this;
    }

    public HeaderView setRight(int resId) {
        setRight(resId, null);
        return this;
    }

    public HeaderView setRight(String str, View.OnClickListener onclick) {
        right.setText(str);
        if (onclick != null) {
            right.setOnClickListener(onclick);
        }
        return this;
    }

    public HeaderView setRight(int resId, View.OnClickListener onclick) {
        right.setText(resId);
        if (onclick != null) {
            right.setOnClickListener(onclick);
        }
        return this;
    }


    public HeaderView setTitle(String str) {
        title.setText(str);
        return this;
    }

    public HeaderView setTitle(int resId) {
        title.setText(resId);
        return this;
    }

    @Override
    public void onClick(View v) {
        activity.finish();
    }

    public HeaderView setToolbarBackground(int resid) {
        toolbar.setBackgroundColor(resid);
        return this;
    }

    public HeaderView setRightIcon(int resid, View.OnClickListener listener) {
        rightIcon.setVisibility(View.VISIBLE);
        rightIcon.setImageResource(resid);
        if (listener != null) {
            rightIcon.setOnClickListener(listener);
        }
        return this;
    }

    public ImageView getRightIcon() {
        return rightIcon;
    }

    public TextView getRight() {
        return right;
    }

}
