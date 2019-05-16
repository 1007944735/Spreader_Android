package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.BusinessModel;
import com.sgevf.spreader.spreaderAndroid.task.UploadBusinessInfoTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;
import com.sgevf.spreader.spreaderAndroid.view.UploadImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessAuthActivity extends BaseLoadingActivity<BusinessModel> {
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
    UploadImageView idcardFront;
    @BindView(R.id.idcard_back)
    UploadImageView idcardBack;
    @BindView(R.id.idcard_license)
    UploadImageView idcardLicense;
    @BindView(R.id.business_logo)
    UploadImageView businessLogo;

    private BusinessModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_business_auth);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("商家认证");
        init();
        initData();
    }

    private void init() {
        idcardFront.setCode(1000);
        idcardBack.setCode(2000);
        idcardLicense.setCode(3000);
        businessLogo.setCode(4000);
    }


    private void initData() {
        model = getIntent().getParcelableExtra("businessInfo");
        if (model != null) {
            businessName.setText(model.bName);
            GlideManager.showImage(this, model.bLogo, businessLogo);
            businessLogo.setFilePath(model.bLogo);
            businessPhone.setText(model.bPhone);
            businessAddress.setText(model.bAddress);
            businessContent.setText(model.bContent);
            businessCredit.setText(model.bSocialCredit);
            GlideManager.showImage(this, model.bIdcardFront, idcardFront);
            idcardFront.setFilePath(model.bIdcardFront);
            GlideManager.showImage(this, model.bIdcardBack, idcardBack);
            idcardBack.setFilePath(model.bIdcardBack);
            GlideManager.showImage(this, model.bLicense, idcardLicense);
            idcardLicense.setFilePath(model.bLicense);
        }
    }

    //商家地址
    @OnClick(R.id.business_address)
    public void businessAddress() {
        startActivityForResult(new Intent(this, AddressSelectorActivity.class), 5000);
    }

    //立即认证
    @OnClick(R.id.submit)
    public void submit() {
        String bName = businessName.getText().toString();
        String logo = businessLogo.getFilePath();
        String bPhone = businessPhone.getText().toString();
        String bAddress = businessAddress.getText().toString();
        String bContent = businessContent.getText().toString();
        String bCredit = businessCredit.getText().toString();
        String front = idcardFront.getFilePath();
        String back = idcardBack.getFilePath();
        String license = idcardLicense.getFilePath();
        new UploadBusinessInfoTask(this, this).setClass(bName, license, logo, front, back, bAddress, bCredit, bPhone, bContent).request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            idcardFront.uploadFile();
        } else if (requestCode == 2000) {
            idcardBack.uploadFile();
        } else if (requestCode == 3000) {
            idcardLicense.uploadFile();
        } else if (requestCode == 4000) {
            businessLogo.uploadFile();
        } else if (requestCode == 5000 && resultCode == 5001) {
            String address = data.getStringExtra("address");
            businessAddress.setText(address);
        }
    }

    @Override
    public void onLoadFinish(BusinessModel businessModel) {
        startActivity(new Intent(this, UploadBusinessSuccessActivity.class));
    }
}
