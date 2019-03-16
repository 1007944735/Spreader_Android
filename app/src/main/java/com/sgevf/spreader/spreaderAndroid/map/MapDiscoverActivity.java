package com.sgevf.spreader.spreaderAndroid.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import utils.WindowHelper;

public class MapDiscoverActivity extends BaseLoadingActivity<MapRedResultModel> {
    private String[] titles = {"排序", "筛选"};
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
    private int maxHeight;
    private PopupWindow popupWindow;
    private LinearLayout tab_1;
    private LinearLayout tab_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_discover);
        ButterKnife.bind(this);
        //创建地图
        initMap(savedInstanceState);
        initSetting();
        initRecyclerView();
        initResultFilter();

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
                } else {
                    resultTip.setVisibility(View.GONE);
                }
                maxHeight = (int) (WindowHelper.getScreenHeight(MapDiscoverActivity.this) * 0.6f);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (view.getHeight() > maxHeight) {
                    params.height = maxHeight;
                    view.setLayoutParams(params);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.d("TAG", "scroll: " + scroll.getHeight());
                float distance;
                float secMax = view.getHeight() - bottomSheetBehavior.getPeekHeight() - resultFilter.getHeight();
                if (v >= 0) {
                    distance = (view.getHeight() - bottomSheetBehavior.getPeekHeight()) * v;
                } else {
                    distance = bottomSheetBehavior.getPeekHeight() * v;
                }
                if (distance <= view.getHeight() * 0.4f && distance > 0) {
                    mapView.setTranslationY(-distance);
                } else if (distance < 0) {
                    mapView.setTranslationY(0);
                }
                if (distance >= secMax) {
                    resultFilter.setTranslationY(-distance + secMax - distance);
                } else {
                    resultFilter.setTranslationY(-distance);
                }
            }
        });
    }

    private void initResultFilter() {
        for (int i = 0; i < titles.length; i++) {
            View view = makeView(i);
            view.setTag(i);
            resultFilter.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        v.setSelected(false);
                        //消失
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        popupWindow = null;
                    } else {
                        clearAllSelected();
                        v.setSelected(true);
                        //出现
                        if (popupWindow == null)
                            createPopup();
                        int i = (int) v.getTag();
                        if (i == 0) {
                            tab_1.setVisibility(View.VISIBLE);
                            tab_2.setVisibility(View.GONE);
                            if (!popupWindow.isShowing()) {
                                popupWindow.showAsDropDown(resultFilter);
                            }
                        } else if (i == 1) {
                            tab_1.setVisibility(View.GONE);
                            tab_2.setVisibility(View.VISIBLE);
                            if (!popupWindow.isShowing()) {
                                popupWindow.showAsDropDown(resultFilter);
                            }
                        }
                    }
                }
            });

        }
    }

    private void clearAllSelected(){
        for(int i=0;i<resultFilter.getChildCount();i++){
            ((View) resultFilter.getChildAt(i)).setSelected(false);
        }
    }

    private void createPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_map_pop, null);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, scroll.getHeight());
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        View mask = view.findViewById(R.id.mask);
        tab_1 = view.findViewById(R.id.tab_1);
        tab_2 = view.findViewById(R.id.tab_2);
        tab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapDiscoverActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });
        tab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapDiscoverActivity.this, "321", Toast.LENGTH_SHORT).show();
            }
        });
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    private View makeView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_map_tab_layout, null);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight=1;
        view.setLayoutParams(params);
        TextView title = view.findViewById(R.id.title);
        title.setText(titles[i]);
        return view;
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
        if (popupWindow != null&&popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        popupWindow = null;
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
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLoadFinish(MapRedResultModel mapRedResultModel) {

    }
}
