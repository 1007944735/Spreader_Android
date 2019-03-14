package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePictureDetailActivity extends BaseActivity {
    @BindView(R.id.photo)
    public ImageView photo;
    @BindView(R.id.check)
    public CheckBox check;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single_picture_detail);
        ButterKnife.bind(this);
        new HeaderView(this)
                .setToolbarBackground(android.R.color.transparent)
                .setRight(R.string.user_center_complete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        init();
    }

    private void init() {
        String url=getIntent().getStringExtra("url");
        GlideManager.showImage(this,url,photo);
        position=getIntent().getIntExtra("position",0);
        boolean isCheck=getIntent().getBooleanExtra("isCheck",false);
        check.setChecked(isCheck);
    }

    @Override
    public void finish() {
        Intent intent=new Intent().putExtra("position",position);
        setResult(1001,intent.putExtra("isCheck",check.isChecked()));
        super.finish();
    }
}
