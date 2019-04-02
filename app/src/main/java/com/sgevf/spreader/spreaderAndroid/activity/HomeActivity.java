package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.HomeFragmentViewPagerAdapter;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.fragment.HomeFixedFragment;
import com.sgevf.spreader.spreaderAndroid.fragment.HomeRandomFragment;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.drawer)
    public DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.tabLayout)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.head_image)
    public ImageView headImage;
    @BindView(R.id.nickName)
    public TextView nickName;
    private HeaderView headerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        ButterKnife.bind(this);
        headerView = new HeaderView(this)
                .setTitle(R.string.home_title)
                .setRightIcon(R.mipmap.icon_home_found, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomeActivity.this, MapDiscoverActivity.class));
                    }
                });
//        headerView.getRight().setBackgroundResource(R.drawable.layout_home_right_tip);
        init();
    }

    private void init() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFixedFragment.newInstance());
        fragments.add(HomeRandomFragment.newInstance());
        HomeFragmentViewPagerAdapter adapter = new HomeFragmentViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getString(R.string.home_fixed_red_packet), getResources().getString(R.string.home_random_red_packet));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserConfig.isLogin(this)) {
            nickName.setText(UserConfig.getNickName(this));
            if ("".equals(UserConfig.getUserHead(this))) {
                GlideManager.circleImage(this, R.mipmap.icon_head_image_default, headImage);
            } else {
                GlideManager.circleImage(this, UserConfig.getUserHead(this), headImage);
            }
        } else {
            nickName.setText(R.string.hone_click_login);
            GlideManager.circleImage(this, R.mipmap.icon_head_image_default, headImage);
        }
    }


    @OnClick(R.id.nickName)
    public void login(View view) {
        if (!UserConfig.isLogin(this)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.exit)
    public void exit(View view) {
        if (UserConfig.isLogin(this)) {
            UserConfig.setLoginStatus(this, false);
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @OnClick(R.id.user_center)
    public void userCenter(View view) {
        if (UserConfig.isLogin(this)) {
            startActivity(new Intent(this, UserCenterActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @OnClick(R.id.wallet)
    public void wallet(View view) {
        if (UserConfig.isLogin(this)) {
            startActivity(new Intent(this, WalletActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @OnClick(R.id.history_release)
    public void historyRelease(View view) {
        if (UserConfig.isLogin(this)) {
            startActivity(new Intent(this, HistoryReleaseActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
