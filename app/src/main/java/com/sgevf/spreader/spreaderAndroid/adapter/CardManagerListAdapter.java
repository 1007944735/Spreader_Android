package com.sgevf.spreader.spreaderAndroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.CardManagerActivity;
import com.sgevf.spreader.spreaderAndroid.model.CardListModel;
import com.sgevf.spreader.spreaderAndroid.task.CardDeleteTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.DateUtils;
import utils.DialogUtils;

public class CardManagerListAdapter extends FactoryAdapter<CardListModel.CardManagerModel> {
    private Activity context;

    public CardManagerListAdapter(Activity context, List<CardListModel.CardManagerModel> items) {
        super(context, items);
        this.context = context;
    }

    @Override
    protected ViewHolderFactory<CardListModel.CardManagerModel> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_card_manager;
    }

    class ViewHolder implements ViewHolderFactory<CardListModel.CardManagerModel>{
        public TextView discountRule;
        public LinearLayout useRule;
        public Button delete;
        public TextView startTime;
        public TextView effectiveTime;

        public ViewHolder(View view) {
            discountRule = view.findViewById(R.id.discountRule);
            useRule = view.findViewById(R.id.useRule);
            delete = view.findViewById(R.id.delete);
            startTime = view.findViewById(R.id.start_time);
            effectiveTime = view.findViewById(R.id.effective_time);
        }

        @Override
        public void init(final CardListModel.CardManagerModel item, int position, FactoryAdapter<CardListModel.CardManagerModel> adapter) {
            try {
                discountRule.setText(item.discountRule);
                if (useRule.getChildCount() != 0) {
                    useRule.removeAllViews();
                }
                String[] rules = item.useRule.split("\\|");
                for (int i = 0; i < rules.length; i++) {
                    TextView textView = new TextView(context);
                    textView.setText("●"+rules[i]);
                    textView.setTextColor(context.getResources().getColor(R.color.colorRipple));
                    textView.setTextSize(10);
                    useRule.addView(textView);
                }
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showConfirm(context, "提示", "是否删除该优惠券，删除后无法恢复!!!", "确定", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new CardDeleteTask(context,context).setClass(item.id).request();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                    }
                });
                startTime.setText("开始时间："+item.startTime);
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NORMAL);
                Date date = sdf.parse(item.effectiveTime);
                StringBuffer sb=new StringBuffer();
                if(date.getDay()!=0){
                    sb.append(date.getDay()+"天");
                }
                if(date.getHours()!=0){
                    sb.append(date.getHours()+"时");
                }
                if(date.getMinutes()!=0){
                    sb.append(date.getMonth()+"分");
                }
                effectiveTime.setText("有效时间："+sb.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
