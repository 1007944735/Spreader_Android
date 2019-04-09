package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.HomeFragmentViewPagerAdapter;
import com.sgevf.spreader.spreaderAndroid.fragment.HomeFragment;
import com.sgevf.spreader.spreaderAndroid.fragment.UserCenterFragment;
import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
//    @BindView(R.id.toolbar)
//    public Toolbar toolbar;
//    @BindView(R.id.tabLayout)
//    public TabLayout tabLayout;
//    @BindView(R.id.viewPager)
//    public ViewPager viewPager;
//    @BindView(R.id.head_image)
//    public ImageView headImage;
//    @BindView(R.id.nickName)
//    public TextView nickName;
//    private HeaderView headerView;
    private static final String[] subTitles={"首页","我"};
    private static final int[] icons={R.drawable.bg_home_tab_home,R.drawable.bg_home_tab_user_center};
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.bottom_navigation_bar)
    public TabLayout bottomNavigationBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        ButterKnife.bind(this);
//        new HeaderView(this)
//                .setTitle(R.string.home_title)
//                .setRightIcon(R.mipmap.icon_home_found, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(HomeActivity.this, MapDiscoverActivity.class));
//                    }
//                });
        init();
    }

    private void init() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(UserCenterFragment.newInstance());
        HomeFragmentViewPagerAdapter adapter = new HomeFragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        bottomNavigationBar.setupWithViewPager(viewPager);
        bottomNavigationBar.removeAllTabs();
        bottomNavigationBar.addTab(bottomNavigationBar.newTab().setCustomView(makeTabView(0)),true);
        bottomNavigationBar.addTab(bottomNavigationBar.newTab().setCustomView(makeTabView(1)));

    }

    private View makeTabView(int position){
        View view= LayoutInflater.from(this).inflate(R.layout.item_home_tab_view,null);
        ImageView icon=view.findViewById(R.id.icon);
        TextView subTitle=view.findViewById(R.id.subTitle);
        icon.setImageResource(icons[position]);
        subTitle.setText(subTitles[position]);
        return view;
    }

    @OnClick(R.id.pub)
    public void pub(){
//        startActivity(new Intent(this, MapDiscoverActivity.class));
        startActivity(new Intent(this, ExpandActivity.class));
    }

//    private void init() {
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(PubActivity.newInstance());
//        fragments.add(HomeRandomFragment.newInstance());
//        HomeFragmentViewPagerAdapter adapter = new HomeFragmentViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getString(R.string.home_fixed_red_packet), getResources().getString(R.string.home_random_red_packet));
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (UserConfig.isLogin(this)) {
//            nickName.setText(UserConfig.getNickName(this));
//            if ("".equals(UserConfig.getUserHead(this))) {
//                GlideManager.circleImage(this, R.mipmap.icon_head_image_default, headImage);
//            } else {
//                GlideManager.circleImage(this, UserConfig.getUserHead(this), headImage);
//            }
//        } else {
//            nickName.setText(R.string.hone_click_login);
//            GlideManager.circleImage(this, R.mipmap.icon_head_image_default, headImage);
//        }
//    }


//    @OnClick(R.id.nickName)
//    public void login(View view) {
//        if (!UserConfig.isLogin(this)) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }
//    }

//       @OnClick(R.id.exit)
//    public void exit(View view) {
//        if (UserConfig.isLogin(this)) {
//            UserConfig.setLoginStatus(this, false);
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//    }
//
//    @OnClick(R.id.user_center)
//    public void userCenter(View view) {
//        if (UserConfig.isLogin(this)) {
//            startActivity(new Intent(this, UserCenterActivity.class));
//        } else {
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//    }
//
//    @OnClick(R.id.wallet)
//    public void wallet(View view) {
//        if (UserConfig.isLogin(this)) {
//            startActivity(new Intent(this, WalletActivity.class));
//        } else {
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//    }
//
//    @OnClick(R.id.history_release)
//    public void historyRelease(View view) {
//        if (UserConfig.isLogin(this)) {
//            startActivity(new Intent(this, HistoryReleaseActivity.class));
//        } else {
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//    }

}
