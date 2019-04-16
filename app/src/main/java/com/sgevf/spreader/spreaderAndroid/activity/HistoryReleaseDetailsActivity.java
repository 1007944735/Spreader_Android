package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideImageLoader;
import com.sgevf.spreader.spreaderAndroid.model.HistorySurplusModel;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseListModel;
import com.sgevf.spreader.spreaderAndroid.task.BaseService;
import com.sgevf.spreader.spreaderAndroid.task.ReFreshHistorySurplusTask;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import utils.AppAlipayUtil;

public class HistoryReleaseDetailsActivity extends BaseLoadingActivity<HistorySurplusModel> {
    private HistoryReleaseListModel.HistoryReleaseModel model;

    @BindView(R.id.titles)
    public TextView titles;
    @BindView(R.id.info)
    public TextView info;
    @BindView(R.id.address)
    public TextView address;
    @BindView(R.id.banner)
    public Banner banner;

    @BindView(R.id.rpInfo)
    public TextView rpInfo;
    @BindView(R.id.duration)
    public TextView duration;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.surplus)
    public TextView surplus;


    @BindView(R.id.orderNo)
    public TextView orderNo;
    @BindView(R.id.createTime)
    public TextView createTime;
    @BindView(R.id.payAmount)
    public TextView payAmount;
    @BindView(R.id.payStatus)
    public TextView payStatus;
    @BindView(R.id.cancel)
    public Button cancel;
    @BindView(R.id.pay)
    public Button pay;
    @BindView(R.id.space)
    public Space space;
    @BindView(R.id.option)
    public LinearLayout option;
    @BindView(R.id.box)
    public ConstraintLayout box;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history_release_details);
        ButterKnife.bind(this);
        model = getIntent().getParcelableExtra("historyDetails");
        new HeaderView(this).setTitle(R.string.history_release_details);
        init();
    }

    private void init() {
        titles.setText(model.title);
        info.setText(model.info);
        address.setText(model.pubAddress);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(Array2List(model));
        banner.setDelayTime(8000);
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        banner.start();

        rpInfo.setText("1".equals(model.type) ? "普通红包" : "随机红包" + "|" + model.amount + "元|" + model.maxNumber + "人");
        duration.setText(model.startTime + "~" + model.endTime);

        orderNo.setText(model.orderNo);
        createTime.setText(model.createTime);
        payAmount.setText(model.amount);
        if ("0".equals(model.status)) {
            payStatus.setText("未付款");
            payStatus.setTextColor(Color.parseColor("#D13D4B"));
            cancel.setVisibility(View.VISIBLE);
            space.setVisibility(View.VISIBLE);
            pay.setVisibility(View.VISIBLE);
        } else if ("1".equals(model.status)) {
            payStatus.setText("已付款");
            cancel.setVisibility(View.VISIBLE);
        } else if ("-1".equals(model.status)) {
            payStatus.setText("已取消");
        }

        if ("-1".equals(model.activiting) || "-2".equals(model.activiting)) {
            option.setVisibility(View.GONE);
            box.setVisibility(View.GONE);
        } else {
            option.setVisibility(View.VISIBLE);
            box.setVisibility(View.VISIBLE);
        }
        if (!"-2".equals(model.activiting)) {
            progressBar.setVisibility(View.VISIBLE);
            new ReFreshHistorySurplusTask(this, this).setClass(model.id + "").request();
        }
    }

    @OnClick(R.id.refresh)
    public void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        surplus.setVisibility(View.GONE);
        new ReFreshHistorySurplusTask(this, this).setClass(model.id + "").request();
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        new BaseService<PubService, String>(this, this) {

            @Override
            public void onSuccess(String s) {
                ToastUtils.Toast(HistoryReleaseDetailsActivity.this, "取消成功");
                Intent intent = new Intent(HistoryReleaseDetailsActivity.this, HistoryReleaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                params.put("orderNo", model.orderNo);
                params.put("redPacketId", model.id + "");
                params.put("isPay", model.status);
                return service.cancelPub(data);
            }
        }.request();
    }

    @OnClick(R.id.pay)
    public void pay() {
        startActivity(new Intent(this, PayActivity.class).putExtra("redPacketId", model.id+"").putExtra("amount", model.amount).putExtra("order", model.orderNo));
    }

    @Override
    public void onLoadFinish(HistorySurplusModel historySurplusModel) {
        progressBar.setVisibility(View.GONE);
        surplus.setText(historySurplusModel.surplus + "个");
        surplus.setVisibility(View.VISIBLE);
    }

    public List<String> Array2List(HistoryReleaseListModel.HistoryReleaseModel model) {
        List<String> images = new ArrayList<>();
        if (model.image1Url != null) images.add(model.image1Url);
        if (model.image2Url != null) images.add(model.image2Url);
        if (model.image3Url != null) images.add(model.image3Url);
        if (model.image4Url != null) images.add(model.image4Url);
        if (model.image5Url != null) images.add(model.image5Url);
        if (model.image6Url != null) images.add(model.image6Url);
        return images;
    }
}
