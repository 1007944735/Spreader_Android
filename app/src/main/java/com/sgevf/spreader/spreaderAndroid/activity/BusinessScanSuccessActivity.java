package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessScanSuccessActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    private AutoBackTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_business_scan_success);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("处理结果");
        timer = new AutoBackTimer(5000, 1000);
        timer.start();
    }

    @OnClick(R.id.back)
    public void back() {
        setResult(2001);
        finish();
    }

    class AutoBackTimer extends CountDownTimer {
        public AutoBackTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            int i = (int) (l / 1000);
            back.setText(i + "s后自动返回扫码界面(立即返回)");
        }

        @Override
        public void onFinish() {
            setResult(2001);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
