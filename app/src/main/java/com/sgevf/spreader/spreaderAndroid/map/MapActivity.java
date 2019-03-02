package com.sgevf.spreader.spreaderAndroid.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Tip;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.InputTipsAdapter;
import com.sgevf.spreader.spreaderAndroid.adapter.LocationResultAdapter;
import com.sgevf.spreader.spreaderAndroid.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.MapUtils;

public class MapActivity extends BaseActivity implements AMap.OnCameraChangeListener, AdapterView.OnItemClickListener {
    @BindView(R.id.aMap)
    MapView mapView;
    @BindView(R.id.location_list)
    ListView locationList;

    AMap aMap;

    MyLocationStyle myLocationStyle;

    UiSettings settings;

//    private boolean followMove = true;

    private SearchView searchView;

    private InputTipsAdapter itAdapter;
    private List<Tip> list;
    private PoiOverlay poiOverlay;

    private LocationResultAdapter lrAdapter;
    private List<PoiItem> poiData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        ButterKnife.bind(this);
        //创建地图
        initMap(savedInstanceState);
        initSetting();
        initSearchView();
    }

    /**
     * 屏幕中心marker 跳动
     */
    private void startJumpAnimation() {

        if (poiOverlay.getFirstMarker() != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = poiOverlay.getFirstMarker().getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this, 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            poiOverlay.getFirstMarker().setAnimation(animation);
            //开始动画
            poiOverlay.getFirstMarker().startAnimation();

        } else {
            Log.e("ama", "screenMarker is null");
        }
    }

    //dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initSearchView() {
        list = new ArrayList<>();
        itAdapter = new InputTipsAdapter(this, list);
        searchView = new SearchView(this)
                .setHint("搜地名,搜地址")
//                .setOnSearchListener(new SearchView.OnSearchListener() {
//                    @Override
//                    public void search(String values) {
//                        if (!TextUtils.isEmpty(values)) {
//                            new MapPoiSearch(MapActivity.this)
//                                    .searchKeyPoi(values, "", "", 0);
//                        }
//                    }
//                })
                .setAdapter(itAdapter)
                .setOnTipExpandListener(new SearchView.OnTipExpandListener() {
                    @Override
                    public void showTip(String key) {
                        if (!TextUtils.isEmpty(key)) {
                            new MapPoiSearch(MapActivity.this)
                                    .setOnAutoTipsFinishListener(new MapPoiSearch.OnAutoTipsFinishListener() {
                                        @Override
                                        public void finish(List<Tip> list) {
                                            MapActivity.this.list.clear();
                                            MapActivity.this.list.addAll(list);
                                            itAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .searchAutoTips(key);
                        } else {
                            MapActivity.this.list.clear();
                            itAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .setOnItemClickListener(new SearchView.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item, int position) {
//                        followMove = false;
                        Toast.makeText(MapActivity.this, ((Tip) item).getPoint().getLongitude() + "", Toast.LENGTH_SHORT).show();
                        double latitude = ((Tip) item).getPoint().getLatitude();
                        double longitude = ((Tip) item).getPoint().getLongitude();
                        poiOverlay.clearAllMarker();
                        if(poiOverlay.getFirstMarker()!=null) {
                            poiOverlay.getFirstMarker().setPosition(new LatLng(latitude, longitude));
                        }else {
                            poiOverlay.addMapMarker(new MapMarker.Builder()
                                    .position(latitude, longitude)
                                    .icon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_map_location))
                                    .anchor(0.5f, 0.5f)
                                    .build());
                        }
                        MapUtils.moveToSpan(aMap, latitude, longitude);
                        MapActivity.this.list.clear();
                        itAdapter.notifyDataSetChanged();
                        searchView.clearValuesFocus(((Tip) item).getName());
                    }
                });
    }

    private void initSetting() {
        settings = aMap.getUiSettings();
        //隐藏定位图标
        settings.setMyLocationButtonEnabled(false);
        //隐藏缩放按钮
        settings.setZoomControlsEnabled(false);
    }

    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        poiOverlay = new PoiOverlay(aMap);

        poiData=new ArrayList<>();
        lrAdapter=new LocationResultAdapter(this,poiData);
        locationList.setAdapter(lrAdapter);
        locationList.setOnItemClickListener(this);

        myLocationStyle = new MyLocationStyle();
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        myLocationStyle.interval(2000);

        myLocationStyle.radiusFillColor(0);
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeWidth(0);

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
//                if (followMove) {
                    MapUtils.moveToSpan(aMap, location.getLatitude(), location.getLongitude(), 18);
                    poiOverlay.addMapMarker(new MapMarker.Builder()
                            .position(location.getLatitude(), location.getLongitude())
                            .icon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_map_location))
                            .anchor(0.5f, 0.5f)
                            .build());
//                }
            }
        });

//        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
//            @Override
//            public void onTouch(MotionEvent motionEvent) {
//                if (followMove) {
//                    followMove = false;
//                }
//            }
//        });
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        poiOverlay.getFirstMarker().setPosition(cameraPosition.target);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLonPoint p = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
        new MapPoiSearch(MapActivity.this)
                .searchGeocoder(p,200)
                .setKeyFinishListener(new MapPoiSearch.OnKeyFinishListener() {
                    @Override
                    public void finish(List<PoiItem> pois) {
                        poiData.clear();
                        poiData.addAll(pois);
                        lrAdapter.notifyDataSetChanged();
                    }
                });
        startJumpAnimation();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.putExtra("poi",poiData.get(position));
        setResult(2000,intent);
        finish();
    }
}
