package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserCardModel;
import com.sgevf.spreader.spreaderAndroid.task.PollQueryUserCardTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.QrCodeUtils;

public class QRCodeCouponActivity extends BaseLoadingActivity<UserCardModel> {
    @BindView(R.id.qrCode)
    ImageView qrCode;

    private Integer couponId;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qrcode);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("二维码");
        couponId = getIntent().getIntExtra("couponId", 0);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                new PollQueryUserCardTask(QRCodeCouponActivity.this, QRCodeCouponActivity.this).setClass(couponId).request();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String json = toJson();
        qrCode.setImageBitmap(QrCodeUtils.createQrCode(json,200, 200));
    }

    public String toJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("couponId", couponId);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLoadFinish(UserCardModel userCardModel) {
        if ("0".equals(userCardModel.isUse)) {
            //再次查询
            handler.postDelayed(runnable, 3000);
        } else if ("1".equals(userCardModel.isUse)) {
            startActivity(new Intent(this, UserCardSuccessActivity.class));
        }
    }
}
