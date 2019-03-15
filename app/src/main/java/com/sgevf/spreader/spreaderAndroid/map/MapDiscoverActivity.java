package com.sgevf.spreader.spreaderAndroid.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.MapDiscoverBottomSheetAdapter;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.MapUtils;

public class MapDiscoverActivity extends BaseLoadingActivity<MapRedResultModel> {
    @BindView(R.id.aMap)
    MapView mapView;
    @BindView(R.id.bottom_sheet)
    RecyclerView bottomSheet;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.result_tip)
    TextView resultTip;
    @BindView(R.id.result_filter)
    LinearLayout resultFilter;
    @BindView(R.id.location)
    ImageView location;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    boolean onlyOnce = true;
    UiSettings settings;
    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_discover);
        ButterKnife.bind(this);
        //创建地图
        initMap(savedInstanceState);
        initSetting();
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i + "");
        }
        MapDiscoverBottomSheetAdapter adapter = new MapDiscoverBottomSheetAdapter(this, list);
        bottomSheet.setLayoutManager(new LinearLayoutManager(this));
        bottomSheet.setAdapter(adapter);

        bottomSheetBehavior = BottomSheetBehavior.from(scroll);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    resultTip.setVisibility(View.VISIBLE);
                    resultFilter.setVisibility(View.GONE);
                } else if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    resultTip.setVisibility(View.GONE);
                    resultFilter.setVisibility(View.VISIBLE);
                } else {
                    resultTip.setVisibility(View.GONE);
                    resultFilter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float distance;
                if (v > 0) {
                    distance = view.getHeight() * v;
                } else {
                    distance = bottomSheetBehavior.getPeekHeight() * v;
                }
                if (distance <= bottomSheetBehavior.getPeekHeight() && distance > 0) {
                    mapView.setTranslationY(-distance);
                } else if (distance < 0) {
                    mapView.setTranslationY(0);
                }
            }
        });
    }

    private void initSetting() {
        settings = aMap.getUiSettings();
        settings.setZoomControlsEnabled(false);
        settings.setMyLocationButtonEnabled(false);
    }

    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000);

        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeWidth(0);

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (onlyOnce) {
                    MapUtils.moveToSpan(aMap, location.getLatitude(), location.getLongitude(), 18, 30, 0);
                    onlyOnce = false;
                }
            }
        });
    }

    @OnClick(R.id.location)
    public void location(View view) {
        //定位
        MapLocationHelper helper = new MapLocationHelper(this);
        helper.startOnceLocation();
        helper.setLocationListener(new MapLocationHelper.LocationListener() {
            @Override
            public void onLocationChange(AMapLocation location) {
                MapUtils.moveToSpan(aMap, location.getLatitude(), location.getLongitude(), 18, 30, 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停地图绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLoadFinish(MapRedResultModel mapRedResultModel) {

    }
}
