package com.sgevf.spreader.spreaderAndroid.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.AndroidBugsSolution;

public class ExpandActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.floatButton)
    public FloatingActionButton floatButton;
    @BindView(R.id.input)
    public EditText input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_expand);
        ButterKnife.bind(this);
        AndroidBugsSolution.assistActivity(this,null);
        new HeaderView(this).setTitle(R.string.history_release);

    }

    @OnClick(R.id.floatButton)
    public void popMore(View view){
        Dialog dialog=new Dialog(this,R.style.DialogTheme);
        dialog.setContentView(R.layout.dialog_expand_more);
        WindowManager.LayoutParams p=dialog.getWindow().getAttributes();
        p.width= WindowManager.LayoutParams.MATCH_PARENT;
        p.height= WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.show();
        dialog.findViewById(R.id.picture).setOnClickListener(this);
        dialog.findViewById(R.id.video).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.picture){
            startActivity(new Intent(this, CustomPhotoActivity.class));
        }else if(v.getId()==R.id.video){

        }
    }
}
