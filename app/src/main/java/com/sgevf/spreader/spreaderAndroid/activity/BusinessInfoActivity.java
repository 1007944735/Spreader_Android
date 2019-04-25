package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.BusinessModel;
import com.sgevf.spreader.spreaderAndroid.task.SearchBusinessInfoTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessInfoActivity extends BaseLoadingActivity<BusinessModel> {
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.business_logo)
    ImageView businessLogo;
    @BindView(R.id.business_phone)
    TextView businessPhone;
    @BindView(R.id.business_address)
    TextView businessAddress;
    @BindView(R.id.business_content)
    TextView businessContent;
    @BindView(R.id.busienss_credit)
    TextView busienssCredit;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.idcard_front)
    ImageView idcardFront;
    @BindView(R.id.idcard_back)
    ImageView idcardBack;
    @BindView(R.id.business_license)
    ImageView businessLicense;

    private BusinessModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_business_info);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("商家信息").setRight("修改", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BusinessInfoActivity.this,BusinessAuthActivity.class).putExtra("businessInfo",model));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SearchBusinessInfoTask(this, this).request();
    }

    @Override
    public void onLoadFinish(BusinessModel model) {
        this.model=model;
        businessName.setText(model.bName);
        GlideManager.showImage(this, model.bLogo, businessLogo);
        businessPhone.setText(model.bPhone);
        businessAddress.setText(model.bAddress);
        businessContent.setText(model.bContent);
        busienssCredit.setText(model.bSocialCredit);
        if ("0".equals(model.status)) {
            status.setText("未注册");
        } else if ("1".equals(model.status)) {
            status.setText("已注册");
        } else if ("2".equals(model.status)) {
            status.setText("审核中");
        } else if ("3".equals(model.status)) {
            status.setText("审核不通过");
        }
        GlideManager.showImage(this, model.bIdcardFront, idcardFront);

        GlideManager.showImage(this, model.bIdcardBack, idcardBack);

        GlideManager.showImage(this, model.bLicense, businessLicense);
    }
}
