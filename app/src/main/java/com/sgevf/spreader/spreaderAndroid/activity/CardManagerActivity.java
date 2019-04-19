package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardManagerActivity extends BaseLoadingActivity<List<CardListModel.CardManagerModel>> {
    public static final String MANAGER = "manager";
    public static final String SELECT = "select";
    @BindView(R.id.cardList)
    public ListView cardList;
    private CardManagerListAdapter adapter;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_card_list_manager);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("优惠券管理").setRight("添加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CardManagerActivity.this, CardAddActivity.class), 1001);
            }
        });
        init();
    }

    private void init() {
        type = getIntent().getStringExtra("type");

        new CardListTask(this, this).request();
    }

    @Override
    public void onLoadFinish(List<CardListModel.CardManagerModel> cardManagerModels) {
        adapter = new CardManagerListAdapter(this, cardManagerModels, type);
        cardList.setAdapter(adapter);
    }

    public void deleteSuccess(String s) {
        ToastUtils.Toast(this, "删除成功");
        new CardListTask(this, this).request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002) {
            new CardListTask(this, this).request();
        }
    }

    @Override
    public void finish() {
        List<CardListModel.CardManagerModel> items = adapter.getItems();
        Intent intent = new Intent().putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
        setResult(2002,intent);
        super.finish();
    }
}
