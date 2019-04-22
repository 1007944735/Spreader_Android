package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.QRCodeCouponActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponCard;

import java.util.List;

public class UserCouponCardListAdapter extends FactoryAdapter<UserCouponCard.Coupon> {
    private Context context;

    public UserCouponCardListAdapter(Context context, List<UserCouponCard.Coupon> items) {
        super(context, items);
        this.context = context;
    }

    @Override
    protected ViewHolderFactory<UserCouponCard.Coupon> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_user_coupon_card;
    }

    private class ViewHolder implements ViewHolderFactory<UserCouponCard.Coupon> {
        TextView discountRule;
        LinearLayout useRule;
        TextView time;
        Button used;

        public ViewHolder(View view) {
            discountRule = view.findViewById(R.id.discountRule);
            useRule = view.findViewById(R.id.useRule);
            time = view.findViewById(R.id.time);
            used = view.findViewById(R.id.used);
        }

        @Override
        public void init(final UserCouponCard.Coupon item, int position, FactoryAdapter<UserCouponCard.Coupon> adapter) {
            discountRule.setText(item.discountRule);
            if (useRule.getChildCount() != 0) {
                useRule.removeAllViews();
            }
            String[] rules = item.useRule.split("\\|");
            for (int i = 0; i < rules.length; i++) {
                TextView textView = new TextView(context);
                textView.setText("●" + rules[i]);
                textView.setTextColor(context.getResources().getColor(R.color.colorRipple));
                textView.setTextSize(10);
                useRule.addView(textView);
            }
            time.setText(item.getTime + "~" + item.endTime);
            used.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,QRCodeCouponActivity.class);
                    intent.putExtra("couponId",item.id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
