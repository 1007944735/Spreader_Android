package com.sgevf.spreader.spreaderAndroid.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.help.Tip;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.InputTipsAdapter;
import com.sgevf.spreader.spreaderAndroid.view.SearchView;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity {
    @BindView(R.id.aMap)
    MapView mapView;

    AMap aMap;

    MyLocationStyle myLocationStyle;

    UiSettings settings;

    private boolean followMove = true;

    private SearchView searchView;

    private InputTipsAdapter adapter;
    private List<Tip> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        ButterKnife.bind(this);
        //创建地图
        initMap(savedInstanceState);
        initSetting();

        list=new ArrayList<>();
        adapter=new InputTipsAdapter(this,list);
        searchView=new SearchView(this)
                .setHint("搜地名,搜地址")
                .setOnSearchListener(new SearchView.OnSearchListener() {
                    @Override
                    public void search(String values) {
                        if(!TextUtils.isEmpty(values)){
                            new MapPoiSearch(MapActivity.this)
                                    .searchKeyPoi(values,"","",0);
                        }
                    }
                })
                .setAdapter(adapter)
                .setOnTipExpandListener(new SearchView.OnTipExpandListener() {
                    @Override
                    public void showTip(String key) {
                        if (!TextUtils.isEmpty(key)) {
                            new MapPoiSearch(MapActivity.this)
                                    .setOnFinishListener(new MapPoiSearch.OnFinishListener() {
                                        @Override
                                        public void finish(List<Tip> list) {
                                            MapActivity.this.list.clear();
                                            MapActivity.this.list.addAll(list);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                                    .searchAutoTips(key);
                        }else {
                            MapActivity.this.list.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        searchView.getTipList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void initSetting() {
        settings = aMap.getUiSettings();
        //隐藏定位图标
        settings.setMyLocationButtonEnabled(false);
    }

    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        myLocationStyle = new MyLocationStyle();
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
                if (followMove) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });

        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (followMove) {
                    followMove = false;
                }
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


}
