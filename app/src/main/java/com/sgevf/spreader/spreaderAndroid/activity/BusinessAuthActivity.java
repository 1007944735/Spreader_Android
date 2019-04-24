package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessAuthActivity extends BaseLoadingActivity<String> {
    @BindView(R.id.business_name)
    EditText businessName;
    @BindView(R.id.business_phone)
    EditText businessPhone;
    @BindView(R.id.business_address)
    TextView businessAddress;
    @BindView(R.id.business_content)
    EditText businessContent;
    @BindView(R.id.busienss_credit)
    EditText businessCredit;
    @BindView(R.id.idcard_front)
    ImageView idcardFront;
    @BindView(R.id.idcard_back)
    ImageView idcardBack;
    @BindView(R.id.idcard_license)
    ImageView idcradLicense;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_business_auth);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("商家认证");

    }

    //商家地址
    @OnClick(R.id.business_address)
    public void businessAddress() {

    }

    //身份证正面
    @OnClick(R.id.idcard_front)
    public void idcardFront() {

    }

    //身份证背面
    @OnClick(R.id.idcard_back)
    public void idcardBack() {

    }

    //营业执照
    @OnClick(R.id.idcard_license)
    public void idcradLicense() {

    }

    //立即认证
    @OnClick(R.id.submit)
    public void submit() {

    }

    @Override
    public void onLoadFinish(String s) {

    }
}
