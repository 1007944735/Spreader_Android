package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.HomeFragmentViewPagerAdapter;
import com.sgevf.spreader.spreaderAndroid.fragment.HomeFixedFragment;
import com.sgevf.spreader.spreaderAndroid.fragment.HomeRandomFragment;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.drawer)
    public DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.tabLayout)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.home_title);
        init();

    }

    private void init() {
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(HomeFixedFragment.newInstance());
        fragments.add(HomeRandomFragment.newInstance());
        HomeFragmentViewPagerAdapter adapter=new HomeFragmentViewPagerAdapter(getSupportFragmentManager(),fragments,getResources().getString(R.string.home_fixed_red_packet),getResources().getString(R.string.home_random_red_packet));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
