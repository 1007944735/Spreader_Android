package com.sgevf.spreader.spreaderAndroid.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.HomeActivity;

import utils.WindowHelper;

public class HeaderView implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView title;
    private TextView right;
    private DrawerLayout drawerLayout;
    private Activity activity;

    public HeaderView(Activity activity) {
        this.activity = activity;
        toolbar = activity.findViewById(R.id.toolbar);
        title = activity.findViewById(R.id.title);
        right = activity.findViewById(R.id.right);
        WindowHelper.setViewPaddingTop(activity, toolbar);
        toolbar.setNavigationOnClickListener(this);
        if (isHomeActivity()) {
            toolbar.setNavigationIcon(R.mipmap.icon_header_menu);
        }
    }

    public HeaderView setRight(String str) {
        setRight(str,null);
        return this;
    }

    public HeaderView setRight(int resId) {
        setRight(resId,null);
        return this;
    }

    public HeaderView setRight(String str, View.OnClickListener onclick) {
        right.setText(str);
        if(onclick!=null){
            right.setOnClickListener(onclick);
        }
        return this;
    }

    public HeaderView setRight(int resId, View.OnClickListener onclick) {
        right.setText(resId);
        if(onclick!=null){
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
        if (isHomeActivity()) {
            drawerLayout = activity.findViewById(R.id.drawer);
            if (drawerLayout.isDrawerOpen(Gravity.START)) {
                drawerLayout.closeDrawer(Gravity.START);
            } else {
                drawerLayout.openDrawer(Gravity.START);
            }
        } else {
            activity.finish();
        }
    }

    public HeaderView setToolbarBackground(int resid){
        toolbar.setBackgroundColor(resid);
        return this;
    }

    /**
     * 判断是否是homeActivity
     *
     * @return
     */
    private boolean isHomeActivity() {
        return activity instanceof HomeActivity;
    }

}
