package com.sgevf.spreader.spreaderAndroid.map;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity {
    @BindView(R.id.aMap)
    MapView mapView;

    AMap aMap;

    MyLocationStyle myLocationStyle;

    private boolean followMove=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        ButterKnife.bind(this);
        //创建地图
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        myLocationStyle=new MyLocationStyle();
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000);

        myLocationStyle.radiusFillColor(0);
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeWidth(0);

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(followMove){
//                    Log.d("TAG", "onLocationChange: " + location.getLatitude());
//                    Log.d("TAG", "onLocationChange: " + location.getLongitude());

                    LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    aMap.addMarker(new MapMarker.Builder().position(location.getLatitude(),location.getLongitude()).title("1").icon(BitmapFactory.decodeResource(getResources(),R.mipmap.icon_map_rede_packet)).build());
                }
            }
        });

        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if(followMove){
                    followMove=false;
                }
            }
        });

//        MapLocationHelper helper=new MapLocationHelper(this);
//        helper.setLocationListener(new MapLocationHelper.LocationListener() {
//            @Override
//            public void onLocationChange(AMapLocation location) {
//                Log.d("TAG", "onLocationChange: "+location.getLongitude());
//                Log.d("TAG", "onLocationChange: "+location.getLatitude());
//
//            }
//        });
//        helper.startLocation();
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


}
