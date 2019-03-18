package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletWithdrawActivity extends BaseActivity {
    @BindView(R.id.count)
    public EditText count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_withdraw);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.wallet_withdraw);

    }

    @OnClick(R.id.confirm)
    public void confirm(View view) {
        String m = count.getText().toString().trim();
    }
}
