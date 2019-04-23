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
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.HomeFragmentViewPagerAdapter;
import com.sgevf.spreader.spreaderAndroid.fragment.HomeFragment;
import com.sgevf.spreader.spreaderAndroid.fragment.UserCenterFragment;
import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.SlideShowModel;
import com.sgevf.spreader.spreaderAndroid.task.HomeSlideShowTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseLoadingActivity<SlideShowModel> {
    private static final String[] subTitles = {"首页", "我"};
    private static final int[] icons = {R.drawable.bg_home_tab_home, R.drawable.bg_home_tab_user_center};
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.bottom_navigation_bar)
    public TabLayout bottomNavigationBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        ButterKnife.bind(this);
        new HomeSlideShowTask(this, this).request();
    }

    private View makeTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_home_tab_view, null);
        ImageView icon = view.findViewById(R.id.icon);
        TextView subTitle = view.findViewById(R.id.subTitle);
        icon.setImageResource(icons[position]);
        subTitle.setText(subTitles[position]);
        return view;
    }

    @OnClick(R.id.pub)
    public void pub() {
        startActivity(new Intent(this, ExpandActivity.class));
    }

    @Override
    public void onLoadFinish(SlideShowModel slideShowModel) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance(slideShowModel.list));
        fragments.add(UserCenterFragment.newInstance());
        HomeFragmentViewPagerAdapter adapter = new HomeFragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        bottomNavigationBar.setupWithViewPager(viewPager);
        bottomNavigationBar.removeAllTabs();
        bottomNavigationBar.addTab(bottomNavigationBar.newTab().setCustomView(makeTabView(0)), true);
        bottomNavigationBar.addTab(bottomNavigationBar.newTab().setCustomView(makeTabView(1)));
    }
}
