package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.CardManagerListAdapter;
import com.sgevf.spreader.spreaderAndroid.model.CardListModel;
import com.sgevf.spreader.spreaderAndroid.task.CardListTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardManagerActivity extends BaseLoadingActivity<List<CardListModel.CardManagerModel>> implements AdapterView.OnItemClickListener {
    @BindView(R.id.cardList)
    public ListView cardList;
    private CardManagerListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_card_list_manager);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("优惠券管理").setRight("添加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardManagerActivity.this, CardAddActivity.class));
            }
        });
        init();
        new CardListTask(this,this).request();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CardListTask(this,this).request();
    }

    private void init() {
        cardList.setOnItemClickListener(this);
    }

    @Override
    public void onLoadFinish(List<CardListModel.CardManagerModel> cardManagerModels) {
        adapter = new CardManagerListAdapter(this, cardManagerModels);
        cardList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void deleteSuccess(String s){
        ToastUtils.Toast(this,"删除成功");
        new CardListTask(this,this).request();
    }
}
