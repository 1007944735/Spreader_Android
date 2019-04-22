package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.UserCouponListAdapter;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponListModel;
import com.sgevf.spreader.spreaderAndroid.task.UserCouponTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCardActivity extends BaseLoadingActivity<UserCouponListModel> {
    @BindView(R.id.user_coupon_cards)
    ListView userCouponCards;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_coupon_card);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("优惠券");
        new UserCouponTask(this, this).request();

    }

    @Override
    public void onLoadFinish(UserCouponListModel userCouponListModel) {
        UserCouponListAdapter adapter = new UserCouponListAdapter(this, userCouponListModel.list);
        userCouponCards.setAdapter(adapter);
    }
}
