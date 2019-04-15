package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.CashWithdrawModel;
import com.sgevf.spreader.spreaderAndroid.model.UserAccountModel;
import com.sgevf.spreader.spreaderAndroid.task.WithdrawTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletWithdrawActivity extends BaseLoadingActivity<CashWithdrawModel> {
    @BindView(R.id.count)
    public EditText count;
    @BindView(R.id.balance)
    public TextView balance;
    @BindView(R.id.confirm)
    public Button confirm;

    private UserAccountModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_withdraw);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.wallet_withdraw);
        init();
    }

    private void init() {
        model = getIntent().getParcelableExtra("model");
        balance.setText("当前可用余额" + model.balance + "元");
        count.addTextChangedListener(new EditChangeListener(confirm, Double.valueOf(model.balance)));
    }

    @OnClick(R.id.confirm)
    public void confirm(View view) {
        String m = count.getText().toString().trim();
        new WithdrawTask(this, this).setClass(m, model.alipayAccount).request();
    }

    @Override
    public void onLoadFinish(CashWithdrawModel model) {
        startActivity(new Intent(this, WalletHistoryWithdrawDetailsActivity.class).putExtra("id", model.withdrawId).putExtra("from",WalletHistoryWithdrawDetailsActivity.FROM_WALLET));
    }


    class EditChangeListener implements TextWatcher {
        private Button confirm;
        private Double count;

        public EditChangeListener(Button confirm, Double count) {
            this.confirm = confirm;
            this.count = count;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().isEmpty() && Double.valueOf(s.toString()) < count) {
                confirm.setEnabled(true);
            } else {
                confirm.setEnabled(false);
            }
        }
    }
}
