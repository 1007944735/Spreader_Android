package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.QRCodeCouponActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponCard;
import com.sgevf.spreader.spreaderAndroid.view.SuperListView;

import java.util.List;

public class UserCouponListAdapter extends FactoryAdapter<UserCouponCard> {
    private Context context;

    public UserCouponListAdapter(Context context, List<UserCouponCard> items) {
        super(context, items);
        this.context = context;
    }

    @Override
    protected ViewHolderFactory<UserCouponCard> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_user_coupon;
    }


    private class ViewHolder implements ViewHolderFactory<UserCouponCard> {
        SuperListView cardList;
        ImageView sellerLogo;
        TextView sellerName;

        public ViewHolder(View view) {
            cardList = view.findViewById(R.id.cardList);
            sellerLogo = view.findViewById(R.id.sellerLogo);
            sellerName = view.findViewById(R.id.sellerName);
        }

        @Override
        public void init(UserCouponCard item, int position, FactoryAdapter<UserCouponCard> adapter) {
            GlideManager.roundImage(context, item.sellerLogo, sellerLogo, 10f);
            sellerName.setText(item.sellerName);
            UserCouponCardListAdapter uccla = new UserCouponCardListAdapter(context, item.cards);
            cardList.setAdapter(uccla);
        }
    }
}
