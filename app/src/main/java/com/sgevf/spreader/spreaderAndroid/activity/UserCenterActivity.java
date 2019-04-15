package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCenterActivity extends BaseActivity {
    @BindView(R.id.userName)
    public TextView userName;
    @BindView(R.id.nickName)
    public TextView nickName;
    @BindView(R.id.phone)
    public TextView phone;
    @BindView(R.id.head)
    public ImageView head;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_user_center);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.user_center);
        init();
    }

    private void init() {
        GlideManager.circleImage(this, UserConfig.getUserHead(this), head);
        userName.setText(UserConfig.getUserName(this));
        nickName.setText(UserConfig.getNickName(this));
        String p = UserConfig.getUserPhone(this);
        if (!TextUtils.isEmpty(p))
            phone.setText(p.substring(0, 3) + "****" + p.substring(7));
    }

    @OnClick(R.id.p_nickName)
    public void updateNickName() {
        Intent intent = new Intent(this, UpdateUserActivity.class);
        intent.putExtra("type", 1);
        startActivityForResult(intent, 1000);

    }

    @OnClick(R.id.p_phone)
    public void updatePhone() {
        Intent intent = new Intent(this, UpdateUserActivity.class);
        intent.putExtra("type", 2);
        startActivityForResult(intent, 2000);
    }

    @OnClick(R.id.ali)
    public void bindAli() {
        startActivity(new Intent (this,BindAlipayActivity.class));
    }

    @OnClick(R.id.resetPassword)
    public void resetPassword() {
        Intent intent = new Intent(this, UpdateUserActivity.class);
        intent.putExtra("type", 3);
        startActivityForResult(intent, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String d = null;
        if (data != null) {
            d = data.getStringExtra("data");
        }
        if (requestCode == 1000 && resultCode == 1001) {
            //昵称
            nickName.setText(d);
        } else if (requestCode == 2000 && resultCode == 2001) {
            //手机号
            phone.setText(d);
        } else if (requestCode == 3000 && resultCode == 3001) {
            //密码
            UserConfig.setLoginStatus(this, false);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
