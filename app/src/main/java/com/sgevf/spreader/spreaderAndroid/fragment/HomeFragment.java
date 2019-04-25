package com.sgevf.spreader.spreaderAndroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.BusinessAuthActivity;
import com.sgevf.spreader.spreaderAndroid.activity.BusinessInfoActivity;
import com.sgevf.spreader.spreaderAndroid.activity.CardManagerActivity;
import com.sgevf.spreader.spreaderAndroid.activity.ExpandActivity;
import com.sgevf.spreader.spreaderAndroid.activity.HistoryReleaseActivity;
import com.sgevf.spreader.spreaderAndroid.activity.QrCodeCameraActivity;
import com.sgevf.spreader.spreaderAndroid.activity.UserCardActivity;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingFragment;
import com.sgevf.spreader.spreaderAndroid.adapter.AdvertisingListAdapter;
import com.sgevf.spreader.spreaderAndroid.glide.GlideImageLoader;
import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseListModel;
import com.sgevf.spreader.spreaderAndroid.model.HomeAdvertisingListModel;
import com.sgevf.spreader.spreaderAndroid.task.BaseService;
import com.sgevf.spreader.spreaderAndroid.task.HomeAdvertisingListTask;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import utils.WindowHelper;

public class HomeFragment extends BaseLoadingFragment<HomeAdvertisingListModel> {
    @BindView(R.id.banner)
    public Banner banner;
    @BindView(R.id.bar)
    public Toolbar toolbar;
    @BindView(R.id.advertising_list)
    public RecyclerView advertisingList;

    private Context context;
    private List<String> list;

    public static HomeFragment newInstance(List<String> list) {

        Bundle args = new Bundle();
        args.putStringArrayList("slideshow", (ArrayList<String>) list);
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
        WindowHelper.setViewPaddingTop(getActivity(), toolbar);
        if (getArguments() != null) {
            list = getArguments().getStringArrayList("slideshow");
        } else {
            list = new ArrayList<>();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(list);
        banner.setDelayTime(8000);
        banner.start();
        new HomeAdvertisingListTask(getActivity(), this).request();
    }

    @OnClick(R.id.business_0)
    public void business_0() {

    }

    @OnClick(R.id.business_1)
    public void business_1() {
        new BaseService<UserService, String>(getActivity(), this) {

            @Override
            public void onSuccess(String s) {
                if ("0".equals(s)) {
                    //未注册
                    startActivity(new Intent(context, BusinessAuthActivity.class));
                    ToastUtils.Toast(context, "商家未注册");
                } else if ("1".equals(s)) {
                    //已注册
                    startActivity(new Intent(context, ExpandActivity.class));
                } else if ("2".equals(s)) {
                    //审核中
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核中");
                } else if ("3".equals(s)) {
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核不通过");
                }

            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                return service.checkIsBusiness(data);
            }
        }.request();
    }

    @OnClick(R.id.business_2)
    public void business_2() {
        new BaseService<UserService, String>(getActivity(), this) {

            @Override
            public void onSuccess(String s) {
                if ("0".equals(s)) {
                    //未注册
                    startActivity(new Intent(context, BusinessAuthActivity.class));
                    ToastUtils.Toast(context, "商家未注册");
                } else if ("1".equals(s)) {
                    //已注册
                    startActivity(new Intent(context, CardManagerActivity.class).putExtra("type", CardManagerActivity.MANAGER));
                } else if ("2".equals(s)) {
                    //审核中
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核中");
                } else if ("3".equals(s)) {
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核不通过");
                }
            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                return service.checkIsBusiness(data);
            }
        }.request();
    }

    @OnClick(R.id.business_3)
    public void business_3() {
        new BaseService<UserService, String>(getActivity(), this) {

            @Override
            public void onSuccess(String s) {
                if ("0".equals(s)) {
                    //未注册
                    startActivity(new Intent(context, BusinessAuthActivity.class));
                    ToastUtils.Toast(context, "商家未注册");
                } else if ("1".equals(s)) {
                    //已注册
                    startActivityForResult(new Intent(context, HistoryReleaseActivity.class).putExtra("from", HistoryReleaseActivity.FROM_HOME), 1000);
                } else if ("2".equals(s)) {
                    //审核中
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核中");
                } else if ("3".equals(s)) {
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核不通过");
                }

            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                return service.checkIsBusiness(data);
            }
        }.request();
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
        startActivity(new Intent(context, UserCardActivity.class));
    }

    @OnClick(R.id.personal_3)
    public void personal_3() {

    }

    @OnClick(R.id.function_1)
    public void function_1() {
        new BaseService<UserService, String>(getActivity(), this) {

            @Override
            public void onSuccess(String s) {
                if ("0".equals(s)) {
                    //未注册
                    startActivity(new Intent(context, BusinessAuthActivity.class));
                } else if ("1".equals(s)) {
                    //已注册
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "已注册");
                } else if ("2".equals(s)) {
                    //审核中
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核中");
                } else if ("3".equals(s)) {
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核不通过");
                }
            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                return service.checkIsBusiness(data);
            }
        }.request();
    }

    @OnClick(R.id.function_2)
    public void function_2() {
        new BaseService<UserService, String>(getActivity(), this) {

            @Override
            public void onSuccess(String s) {
                if ("0".equals(s)) {
                    //未注册
                    startActivity(new Intent(context, BusinessAuthActivity.class));
                    ToastUtils.Toast(context, "未注册");
                } else if ("1".equals(s)) {
                    //已注册
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                } else if ("2".equals(s)) {
                    //审核中
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核中");
                } else if ("3".equals(s)) {
                    startActivity(new Intent(context, BusinessInfoActivity.class));
                    ToastUtils.Toast(context, "审核不通过");
                }
            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                return service.checkIsBusiness(data);
            }
        }.request();
    }

    @OnClick(R.id.function_3)
    public void function_3() {

    }

    @OnClick(R.id.function_4)
    public void function_4() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            if (data != null) {
                HistoryReleaseListModel.HistoryReleaseModel model = data.getParcelableExtra("historyDetails");
                startActivity(new Intent(context, QrCodeCameraActivity.class).putExtra("redPacketId", model.id));
            }
        }
    }

    @Override
    public void onLoadFinish(HomeAdvertisingListModel homeAdvertisingListModel) {
        advertisingList.setLayoutManager(new LinearLayoutManager(context));
        AdvertisingListAdapter adapter = new AdvertisingListAdapter(context, homeAdvertisingListModel.list);
        advertisingList.setAdapter(adapter);
    }
}
