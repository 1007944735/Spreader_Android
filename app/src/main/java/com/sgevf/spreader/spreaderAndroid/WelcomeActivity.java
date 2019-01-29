package com.sgevf.spreader.spreaderAndroid;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.sgevf.spreader.http.base.BaseActivity;
import com.sgevf.spreader.http.utils.NetConfig;

import utils.PropertiesUtils;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
//        init();
        initView();
    }

    private void init() {
        NetConfig.URL = PropertiesUtils.getUrl(this);
    }

    private void initView() {

        ImageView anim_path = findViewById(R.id.anim_path);
        anim_path.setVisibility(View.VISIBLE);
        start(anim_path);
    }


    public void start(View view) {
        Drawable drawable = ((ImageView) view).getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
