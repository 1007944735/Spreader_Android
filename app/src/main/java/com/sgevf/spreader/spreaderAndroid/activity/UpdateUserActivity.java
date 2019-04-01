package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.task.UpdateInfoTask;
import com.sgevf.spreader.spreaderAndroid.task.UpdatePasswordTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateUserActivity extends BaseLoadingActivity<String> {
    private int type;//1 修改昵称 2 修改手机号 3 修改密码
    @BindView(R.id.input)
    public EditText input;
    @BindView(R.id.old_pass)
    public EditText oldPass;
    @BindView(R.id.new_pass)
    public EditText newPass;
    @BindView(R.id.re_pass)
    public EditText rePass;
    @BindView(R.id.update)
    public LinearLayout update;

    private String data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_user);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 1:
                new HeaderView(this)
                        .setTitle(R.string.user_center_nickname)
                        .setRight(R.string.user_center_complete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String nickname = input.getText().toString().trim();
                                data=nickname;
                                new UpdateInfoTask(UpdateUserActivity.this,this).setClass(type+"",nickname,"").request();
                            }
                        });
                input.setVisibility(View.VISIBLE);
                input.setText(UserConfig.getNickName(this));
                input.setHint(UserConfig.getNickName(this));
                break;
            case 2:
                new HeaderView(this)
                        .setTitle(R.string.user_center_phone)
                        .setRight(R.string.user_center_complete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String phone = input.getText().toString().trim();
                                data=phone;
                                new UpdateInfoTask(UpdateUserActivity.this,this).setClass(type+"","",phone).request();
                            }
                        });
                input.setVisibility(View.VISIBLE);
                input.setText(UserConfig.getUserPhone(this));
                input.setHint(UserConfig.getUserPhone(this));
                break;
            case 3:
                new HeaderView(this)
                        .setTitle(R.string.user_center_update_password)
                        .setRight(R.string.user_center_complete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String oldP = oldPass.getText().toString().trim();
                                String newP = newPass.getText().toString().trim();
                                String reP = rePass.getText().toString().trim();
                                data=newP;
                                if(!newP.isEmpty()&&newP.equals(reP)) {
                                    new UpdatePasswordTask(UpdateUserActivity.this,UpdateUserActivity.this).setClass(oldP,newP).request();
                                }else {
                                    ToastUtils.Toast(UpdateUserActivity.this,"密码不一致");
                                }


                            }
                        });

                update.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onLoadFinish(String s) {
        ToastUtils.Toast(this,s);
        Intent intent=new Intent();
        intent.putExtra("data",data);
        if(type==1){
            setResult(1001,intent);
        }else if(type==2){
            setResult(2001,intent);
        }else if(type==3){
            setResult(3001,intent);
        }
        finish();
    }
}
