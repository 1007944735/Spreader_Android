package com.sgevf.spreader.spreaderAndroid.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.WalkRouteResult;
import com.autonavi.amap.mapcore.Inner_3dMap_location;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.RedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.RedPacketDetailsTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapNavigationActivity extends BaseLoadingActivity<RedPacketDetailsModel> implements MapPathPlanHelper.MapPathPlanListener, AMap.OnMapLoadedListener, AMap.OnMyLocationChangeListener {
    @BindView(R.id.aMap)
    MapView mapView;

    AMap aMap;
    MyLocationStyle myLocationStyle;

    private int redPacketId;
    private Location location;
    private int tripWay;
    private MapPathPlanHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_navigation);
        ButterKnife.bind(this);
        new HeaderView(this).setToolbarBackground(android.R.color.transparent);
        //创建地图
        init();
        initMap(savedInstanceState);
    }

    private void init() {
        redPacketId = getIntent().getIntExtra("redPacketId", 0);
        location = getIntent().getParcelableExtra("location");
        tripWay = getIntent().getIntExtra("tripWay", 1);
        helper = new MapPathPlanHelper(this);
        helper.setMapPathPlanListener(this);
    }

    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        myLocationStyle.showMyLocation(false);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        registerListener();

    }

    private void registerListener() {
        aMap.setOnMapLoadedListener(this);
        aMap.setOnMyLocationChangeListener(this);
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
    public void walkRoutePlan(WalkRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {

        } else {

        }
    }

    @Override
    public void drivingRoutePlan(DriveRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {

        } else {

        }
    }

    @Override
    public void busRoutePlan(BusRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {

        } else {

        }
    }

    @Override
    public void onMapLoaded() {
        new RedPacketDetailsTask(this, this).setClass(redPacketId, location.getLongitude() + "", location.getLatitude() + "").request();
    }

    @Override
    public void onLoadFinish(RedPacketDetailsModel model) {
        if (tripWay == 1) {
            //步行
            helper.walkPathPlan(location.getLongitude(), location.getLatitude(), Double.valueOf(model.pubLongitude), Double.valueOf(model.pubLatitude));
        } else if (tripWay == 2) {
            //自驾
            helper.drivingPathPlan(location.getLongitude(), location.getLatitude(), Double.valueOf(model.pubLongitude), Double.valueOf(model.pubLatitude));
        } else if (tripWay == 3) {
            //公交
            helper.busPathPlan(location.getLongitude(), location.getLatitude(), Double.valueOf(model.pubLongitude), Double.valueOf(model.pubLatitude), ((Inner_3dMap_location) location).getCityCode());
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.d("TAG", "onMyLocationChange: " + location.toString());
    }
}
