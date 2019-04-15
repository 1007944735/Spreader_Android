package com.sgevf.spreader.spreaderAndroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.BindAlipayActivity;
import com.sgevf.spreader.spreaderAndroid.activity.HistoryReleaseActivity;
import com.sgevf.spreader.spreaderAndroid.activity.HomeActivity;
import com.sgevf.spreader.spreaderAndroid.activity.LoginActivity;
import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.activity.UpdateUserActivity;
import com.sgevf.spreader.spreaderAndroid.activity.WalletActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCenterFragment extends Fragment {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.userName)
    public TextView userName;
    @BindView(R.id.nickName)
    public TextView nickName;
    @BindView(R.id.phone)
    public TextView phone;
    @BindView(R.id.head)
    public ImageView head;
    @BindView(R.id.unLogin)
    LinearLayout unLogin;
    @BindView(R.id.logining)
    LinearLayout logining;
    @BindView(R.id.exit)
    Button exit;

    private Context context;

    public static UserCenterFragment newInstance() {

        Bundle args = new Bundle();

        UserCenterFragment fragment = new UserCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user_center, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        WindowHelper.setViewPaddingTop(getActivity(), toolbar);


    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        if (UserConfig.isLogin(context)) {
            unLogin.setVisibility(View.GONE);
            logining.setVisibility(View.VISIBLE);
            GlideManager.circleImage(this, UserConfig.getUserHead(context), head);
            userName.setText(UserConfig.getUserName(context));
            nickName.setText(UserConfig.getNickName(context));
            String p = UserConfig.getUserPhone(context);
            if (!TextUtils.isEmpty(p))
                phone.setText(p.substring(0, 3) + "****" + p.substring(7));
            exit.setVisibility(View.VISIBLE);
        } else {
            unLogin.setVisibility(View.VISIBLE);
            logining.setVisibility(View.GONE);
            exit.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.p_nickName)
    public void updateNickName() {
        Intent intent = new Intent(context, UpdateUserActivity.class);
        intent.putExtra("type", 1);
        startActivityForResult(intent, 1000);

    }

    @OnClick(R.id.p_phone)
    public void updatePhone() {
        Intent intent = new Intent(context, UpdateUserActivity.class);
        intent.putExtra("type", 2);
        startActivityForResult(intent, 2000);
    }

    @OnClick(R.id.ali)
    public void bindAli() {
        startActivity(new Intent(context, BindAlipayActivity.class));
    }

    @OnClick(R.id.resetPassword)
    public void resetPassword() {
        Intent intent = new Intent(context, UpdateUserActivity.class);
        intent.putExtra("type", 3);
        startActivityForResult(intent, 3000);
    }

    @OnClick(R.id.login)
    public void login() {
        startActivity(new Intent(context, LoginActivity.class));
    }

    @OnClick(R.id.register)
    public void register() {
        startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
            UserConfig.setLoginStatus(context, false);
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        }
    }

//    @OnClick(R.id.nickName)
//    public void login() {
//        if (!UserConfig.isLogin(context)) {
//            Intent intent = new Intent(context, LoginActivity.class);
//            startActivity(intent);
//        }
//    }

    @OnClick(R.id.exit)
    public void exit() {
        if (UserConfig.isLogin(context)) {
            UserConfig.setLoginStatus(context, false);
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

//    @OnClick(R.id.user_center)
//    public void userCenter() {
//        if (UserConfig.isLogin(context)) {
//            startActivity(new Intent(context, UserCenterActivity.class));
//        } else {
//            startActivity(new Intent(context, LoginActivity.class));
//        }
//    }

    @OnClick(R.id.wallet)
    public void wallet() {
        if (UserConfig.isLogin(context)) {
            startActivity(new Intent(context, WalletActivity.class));
        } else {
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @OnClick(R.id.history_release)
    public void historyRelease() {
        if (UserConfig.isLogin(context)) {
            startActivity(new Intent(context, HistoryReleaseActivity.class));
        } else {
            startActivity(new Intent(context, LoginActivity.class));
        }
    }
}
