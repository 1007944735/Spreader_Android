package com.sgevf.spreader.spreaderAndroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.ExpandActivity;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingFragment;
import com.sgevf.spreader.spreaderAndroid.map.MapActivity;
import com.sgevf.spreader.spreaderAndroid.model.ExpandInfoModel;
import com.sgevf.spreader.spreaderAndroid.model.ExpandPhotoModel;
import com.sgevf.spreader.spreaderAndroid.task.PubTask;
import com.sgevf.spreader.spreaderAndroid.view.DatePickerDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.DialogUtils;

public class HomeFixedFragment extends BaseLoadingFragment<String> {
    @BindView(R.id.count)
    public EditText count;
    @BindView(R.id.price)
    public EditText price;
    @BindView(R.id.start)
    public TextView start;
    @BindView(R.id.end)
    public TextView end;
    @BindView(R.id.address)
    public TextView address;

    private ExpandInfoModel infos;
    private PoiItem poi;

    public static HomeFixedFragment newInstance() {
        Bundle args = new Bundle();

        HomeFixedFragment fragment = new HomeFixedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fixed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.start_time)
    public void startTime() {
        DialogUtils.showSelectTime(context, new DatePickerDialog.OnConfirmListener() {
            @Override
            public void select(String time) {
                start.setText(time);
            }
        });
    }

    @OnClick(R.id.end_time)
    public void endTime() {
        DialogUtils.showSelectTime(context, new DatePickerDialog.OnConfirmListener() {
            @Override
            public void select(String time) {
                end.setText(time);
            }
        });
    }

    @OnClick(R.id.release_address)
    public void releaseAddress() {
        startActivityForResult(new Intent(context, MapActivity.class), 1000);
    }

    @OnClick(R.id.expand)
    public void expand() {
        startActivityForResult(new Intent(context, ExpandActivity.class).putExtra("infos",infos), 2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == 1001) {
            poi = data.getParcelableExtra("poi");
            address.setText(poi.getTitle());
        } else if (requestCode == 2000 && resultCode == 2001) {
            infos=data.getParcelableExtra("infos");
        }
    }

    @OnClick(R.id.submit)
    public void submit(){
        PubTask task=new PubTask(getActivity(),this);
        task.params.put("amount",""+Integer.valueOf(count.getText().toString())*Double.valueOf(price.getText().toString()));
        task.params.put("type","1");
        task.params.put("pubLongitude",poi.getLatLonPoint().getLongitude()+"");
        task.params.put("pubLatitude",poi.getLatLonPoint().getLatitude()+"");
        task.params.put("startTime",start.getText().toString());
        task.params.put("endTime",end.getText().toString());
        task.params.put("maxNumber",count.getText().toString());
        task.params.put("pubAddress",address.getText().toString());
        task.params.put("title",infos.title);
        task.params.put("info",infos.info);
        task.params.put("video",new File(infos.video.path));
        for(ExpandPhotoModel picture:infos.pictures) {
            task.params.put("pictures", new File(picture.path));
        }
        task.request();
    }

    @Override
    public void onLoadFinish(String s) {
        count.setText("");
        price.setText("");
        start.setText("");
        end.setText("");
        address.setText("");
        poi=null;
        infos=null;
    }
}
