package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponCheckModel;
import com.sgevf.spreader.spreaderAndroid.task.UseCouponTask;
import com.sgevf.spreader.spreaderAndroid.task.UserCouponCheckTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QrCodeCameraActivity extends BaseLoadingActivity<UserCouponCheckModel> {
    @BindView(R.id.result_icon)
    public ImageView resultIcon;
    @BindView(R.id.result_info)
    public TextView resultInfo;
    @BindView(R.id.discountRule)
    public TextView discountRule;
    @BindView(R.id.useRule)
    public LinearLayout useRule;
    @BindView(R.id.startTime)
    public TextView startTime;
    @BindView(R.id.endTime)
    public TextView endTime;
    @BindView(R.id.couponInfo)
    public LinearLayout couponInfo;
    @BindView(R.id.btn)
    public Button btn;

    private int redPacketId;
    private boolean isRight = false;
    private int couponId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.layout_qrcode_camera);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("扫码结果");
        initData();
        init();
    }

    private void initData() {
        redPacketId = getIntent().getIntExtra("redPacketId", 0);
    }

    private void init() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    try {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Log.d("TAG", "解析结果:" + result);
                        JSONObject jsonObject = new JSONObject(result);
                        couponId = jsonObject.getInt("couponId");
                        new UserCouponCheckTask(this, this).setClass(couponId, redPacketId).request();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Log.d("TAG", "解析二维码失败");
                }
            }
        } else if (requestCode == 2000 && resultCode == 2001) {
            btn.setVisibility(View.GONE);
            resultInfo.setVisibility(View.GONE);
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, 1000);
        }

    }

    @Override
    public void onLoadFinish(UserCouponCheckModel model) {
        switch (model.type) {
            case 0:
                this.isRight = true;
                setResult(true, "优惠券有效");
                initCouponInfo(model);
                couponInfo.setVisibility(View.VISIBLE);
                break;
            case -1:
                this.isRight = false;
                setResult(false, "优惠券已被使用");
                couponInfo.setVisibility(View.GONE);
                break;
            case -2:
                this.isRight = false;
                setResult(false, "无优惠券使用");
                couponInfo.setVisibility(View.GONE);
                break;
            case -3:
                this.isRight = false;
                setResult(false, "优惠券不能使用");
                couponInfo.setVisibility(View.GONE);
                break;
            case -4:
                this.isRight = false;
                setResult(false, "未到优惠时间");
                couponInfo.setVisibility(View.GONE);
                break;
            case -5:
                this.isRight = false;
                setResult(false, "优惠券过期");
                couponInfo.setVisibility(View.GONE);
                break;
        }

    }

    @OnClick(R.id.btn)
    public void btn() {
        if (isRight) {
            //优惠券可用
            if (couponId != 0) {
                new UseCouponTask(this, this).setClass(couponId).request();
            }
        } else {
            //继续扫一扫
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, 1000);
        }
    }

    public void useFinish(String s) {
        //优惠券使用成功（商家界面）
        startActivityForResult(new Intent(this, BusinessScanSuccessActivity.class), 2000);
    }

    private void initCouponInfo(UserCouponCheckModel model) {
        discountRule.setText(model.discountRule);
        String[] rules = model.useRule.split("\\|");
        for (int i = 0; i < rules.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(rules[i]);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(16f);
            useRule.addView(textView);
        }
        startTime.setText(model.getTime);
        endTime.setText(model.endTime);
    }

    private void setResult(boolean isRight, String info) {
        if (isRight) {
            resultInfo.setTextColor(Color.parseColor("#00C07C"));
            resultIcon.setImageResource(R.mipmap.icon_user_coupon_check_right);
            btn.setText("确定");
//            btn.setBackground(getResources().getDrawable(R.drawable.bg_btn_confirm_selector));
        } else {
            resultInfo.setTextColor(Color.parseColor("#F64751"));
            resultIcon.setImageResource(R.mipmap.icon_user_coupon_check_error);
            btn.setText("扫一扫");
//            btn.setBackground(getResources().getDrawable(R.drawable.bg_btn_confirm_selector));
        }
        resultInfo.setText(info);
        btn.setVisibility(View.VISIBLE);
    }
}
