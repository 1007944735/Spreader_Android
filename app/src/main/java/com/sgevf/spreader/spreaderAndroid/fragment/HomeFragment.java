package com.sgevf.spreader.spreaderAndroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.CardManagerActivity;
import com.sgevf.spreader.spreaderAndroid.activity.ExpandActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideImageLoader;
import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {
    @BindView(R.id.banner)
    public Banner banner;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private Context context;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner.setImageLoader(new GlideImageLoader());
        List urls = new ArrayList();
        urls.add("");
        urls.add("");
        urls.add("");
        urls.add("");
        banner.setImages(urls);
        banner.setDelayTime(8000);
        banner.start();
    }

    @OnClick(R.id.business_0)
    public void business_0() {

    }

    @OnClick(R.id.business_1)
    public void business_1() {
        startActivity(new Intent(context, ExpandActivity.class));
    }

    @OnClick(R.id.business_2)
    public void business_2() {
        startActivity(new Intent(context, CardManagerActivity.class).putExtra("type",CardManagerActivity.MANAGER));
    }

    @OnClick(R.id.business_3)
    public void business_3() {

    }

    @OnClick(R.id.personal_0)
    public void personal_0() {

    }

    @OnClick(R.id.personal_1)
    public void personal_1() {
        startActivity(new Intent(context, MapDiscoverActivity.class));
    }

    @OnClick(R.id.personal_2)
    public void personal_2() {

    }

    @OnClick(R.id.personal_3)
    public void personal_3() {

    }

}
