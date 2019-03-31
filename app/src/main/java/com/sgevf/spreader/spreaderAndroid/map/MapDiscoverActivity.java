package com.sgevf.spreader.spreaderAndroid.map;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.sgevf.multimedia.video.VideoThreeActivity;
import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.MapDiscoverBottomSheetAdapter;
import com.sgevf.spreader.spreaderAndroid.glide.GlideImageLoader;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;
import com.sgevf.spreader.spreaderAndroid.model.MapSearchLocationModel;
import com.sgevf.spreader.spreaderAndroid.task.MapSearchTask;
import com.sgevf.spreader.spreaderAndroid.view.FilterOptionView;
import com.sgevf.spreader.spreaderAndroid.view.RedPacketDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.DialogUtils;
import utils.MapUtils;
import utils.WindowHelper;

public class MapDiscoverActivity extends BaseLoadingActivity<MapRedResultModels> implements View.OnClickListener, MapDiscoverBottomSheetAdapter.OnItemClickListener, RedPacketDialog.OnOpenListener, AMap.OnMarkerClickListener, AMap.OnMapTouchListener {
    private String[] titles = {"排序", "筛选"};
    @BindView(R.id.aMap)
    MapView mapView;
    @BindView(R.id.bottom_sheet)
    RecyclerView bottomSheet;
    @BindView(R.id.redPackets)
    NestedScrollView redPackets;
    @BindView(R.id.details)
    NestedScrollView details;
    @BindView(R.id.result_tip)
    TextView resultTip;
    @BindView(R.id.result_filter)
    LinearLayout resultFilter;
    @BindView(R.id.location)
    ImageView location;
    @BindView(R.id.error)
    TextView error;
    @BindView(R.id.details_banner)
    Banner detailsBanner;
    @BindView(R.id.details_layout)
    LinearLayout detailsLayout;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    boolean onlyOnce = true;
    UiSettings settings;
    BottomSheetBehavior redPacketBehavior;
    BottomSheetBehavior detailsBehavior;
    private PopupWindow popupWindow;
    private LinearLayout tab_1;
    private LinearLayout tab_2;
    private TextView curSelectedOrder;
    private FilterOptionView number;
    private FilterOptionView money;
    private FilterOptionView type;
    private String[] numberData;
    private String[] moneyData;
    private String[] typeData;
    private MapDiscoverBottomSheetAdapter adapter;
    private RedPacketDialog dialog;
    private PoiOverlay poiOverlay;
    private LocationHandler handler;
    private MapSearchLocationModel mslm;
    private List<MapRedResultModels.MapRedResultModel> recyclerData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_discover);
        ButterKnife.bind(this);
        handler = new LocationHandler(this);
        init();
        //创建地图
        initMap(savedInstanceState);
        initSetting();
        initRecyclerView();
        initDetails();
        initResultFilter();
    }

    private void init() {
        numberData = getResources().getStringArray(R.array.discover_order_number);
        moneyData = getResources().getStringArray(R.array.discover_order_money);
        typeData = getResources().getStringArray(R.array.discover_order_type);
    }

    private void initRecyclerView() {
        adapter = new MapDiscoverBottomSheetAdapter(this, null);
        adapter.setOnItemClickListener(this);
        bottomSheet.setLayoutManager(new LinearLayoutManager(this));
        bottomSheet.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bottomSheet.setAdapter(adapter);

        int maxHeight = (int) (WindowHelper.getScreenHeight(MapDiscoverActivity.this) * 0.6f);
        ViewGroup.LayoutParams params = redPackets.getLayoutParams();
        params.height = maxHeight;
        redPackets.setLayoutParams(params);

        redPacketBehavior = BottomSheetBehavior.from(redPackets);
        redPacketBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    resultTip.setVisibility(View.VISIBLE);
                    bottomSheet.setVisibility(View.GONE);
                } else {
                    resultTip.setVisibility(View.GONE);
                    bottomSheet.setVisibility(View.VISIBLE);
                }
                if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    mapView.setTranslationY(-WindowHelper.getScreenHeight(MapDiscoverActivity.this) * 0.3f);
                } else {
                    mapView.setTranslationY(0);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float distance;
                float secMax = view.getHeight() - redPacketBehavior.getPeekHeight() - resultFilter.getHeight();
                if (v >= 0) {
                    distance = (view.getHeight() - redPacketBehavior.getPeekHeight()) * v;
                } else {
                    distance = redPacketBehavior.getPeekHeight() * v;
                }
                if (distance >= secMax) {
                    resultFilter.setTranslationY(-distance + secMax - distance);
                } else {
                    resultFilter.setTranslationY(-distance);
                }
            }
        });
    }

    private void initDetails() {
        //设置banner和details的高度
        final int detailsBannerHeight;
        final int screenHeight = WindowHelper.getScreenHeight(this);
        ViewGroup.LayoutParams dp = detailsBanner.getLayoutParams();
        detailsBannerHeight = (int) (screenHeight * 0.3);
        dp.height = detailsBannerHeight;
        detailsBanner.setLayoutParams(dp);
        detailsBanner.setTranslationY(-dp.height);

        ViewGroup.LayoutParams dlp = details.getLayoutParams();
        dlp.height = screenHeight - detailsBannerHeight;
        details.setLayoutParams(dlp);

        detailsBehavior = BottomSheetBehavior.from(details);
        detailsBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                detailsBanner.setTranslationY(-(1 - v) * detailsBannerHeight);
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
                    } else {
                        clearAllSelected();
                        v.setSelected(true);
                        //出现
                        if (popupWindow == null)
                            //选择弹窗
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

    private void clearAllSelected() {
        for (int i = 0; i < resultFilter.getChildCount(); i++) {
            ((View) resultFilter.getChildAt(i)).setSelected(false);
        }
    }

    private void createPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_map_pop, null);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, redPackets.getHeight());
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        View mask = view.findViewById(R.id.mask);
        tab_1 = view.findViewById(R.id.tab_1);
        tab_2 = view.findViewById(R.id.tab_2);
        initOrder(view);
        initFilter(view);
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearAllSelected();
            }
        });
    }

    private void initOrder(View view) {
        TextView smartOrder = view.findViewById(R.id.smartOrder);
        TextView maxPeople = view.findViewById(R.id.maxPeople);
        TextView maxCount = view.findViewById(R.id.maxCount);
        TextView minDistance = view.findViewById(R.id.minDistance);
        smartOrder.setOnClickListener(this);
        maxPeople.setOnClickListener(this);
        maxCount.setOnClickListener(this);
        minDistance.setOnClickListener(this);
    }

    private void initFilter(View view) {
        number = view.findViewById(R.id.number);
        money = view.findViewById(R.id.money);
        type = view.findViewById(R.id.type);
        view.findViewById(R.id.reset).setOnClickListener(this);
        view.findViewById(R.id.confirm).setOnClickListener(this);
        number.setData(numberData);
        money.setData(moneyData);
        type.setData(typeData);
    }


    private View makeView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_map_tab_layout, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
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
        aMap.setOnMarkerClickListener(this);
        aMap.setOnMapTouchListener(this);
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
        mslm = new MapSearchLocationModel();
        mslm.taskType = 1;
        location(mslm);
    }

    /**
     * 定位，并通过handler返回定位信息
     *
     * @param info
     */
    public void location(final MapSearchLocationModel info) {
        MapLocationHelper helper = new MapLocationHelper(this);
        helper.startOnceLocation();
        helper.setLocationListener(new MapLocationHelper.LocationListener() {
            @Override
            public void onLocationChange(AMapLocation location) {
                Message data = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("info", info);
                bundle.putDouble("longitude", location.getLongitude());
                bundle.putDouble("latitude", location.getLatitude());
                data.setData(bundle);
                handler.sendMessage(data);
                MapUtils.moveToSpan(aMap, location.getLatitude(), location.getLongitude(), 18, 30, 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        mapView.onResume();
        if (mslm == null) {
            mslm = new MapSearchLocationModel();
            mslm.taskType = 2;
        }
        location(mslm);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁地图
        mapView.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
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
            clearAllSelected();
            popupWindow.dismiss();
        } else if (detailsBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            detailsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLoadFinish(MapRedResultModels mapRedResultModels) {
        if (mapRedResultModels.list.isEmpty()) {
            error.setVisibility(View.VISIBLE);
            bottomSheet.setVisibility(View.GONE);
            resultTip.setText("暂无结果");
        } else {
            error.setVisibility(View.GONE);
            bottomSheet.setVisibility(View.VISIBLE);
            resultTip.setText("共找到" + mapRedResultModels.list.size() + "个红包");
        }
        recyclerData = mapRedResultModels.list;
        adapter.setData(mapRedResultModels.list);
        refreshPoi(mapRedResultModels.list);
    }

    /**
     * 重新加载地图红包的显示
     */
    private void refreshPoi(List<MapRedResultModels.MapRedResultModel> data) {
        if (poiOverlay == null) {
            poiOverlay = new PoiOverlay(aMap);
        }
        poiOverlay.clearAllMarker();
        for (MapRedResultModels.MapRedResultModel model : data) {
            poiOverlay.addMapMarker(new MapMarker.Builder()
                    .position(Double.valueOf(model.pubLatitude), Double.valueOf(model.pubLongitude))
//                    .icon(BitmapFactory.decodeResource(getResources(),R.mipmap.icon_map_rede_packet))
                    .anchor(0.5f, 0.5f)
                    .build());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smartOrder:
                //暂时无用
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(titles[0]);
                selectOne(v);
                resultFilter.getChildAt(0).setSelected(false);
                popupWindow.dismiss();
                break;
            case R.id.maxPeople:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_max_people);
                selectOne(v);
                mslm.taskType = 2;
                mslm.orderType = "1";
                location(mslm);
                resultFilter.getChildAt(0).setSelected(false);
                popupWindow.dismiss();
                break;
            case R.id.maxCount:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_max_count);
                selectOne(v);
                mslm.taskType = 2;
                mslm.orderType = "2";
                location(mslm);
                resultFilter.getChildAt(0).setSelected(false);
                popupWindow.dismiss();
                break;
            case R.id.minDistance:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_min_distance);
                selectOne(v);
                mslm.taskType = 2;
                mslm.orderType = "3";
                location(mslm);
                resultFilter.getChildAt(0).setSelected(false);
                popupWindow.dismiss();
                break;
            case R.id.reset:
                number.reset();
                money.reset();
                type.reset();
                break;
            case R.id.confirm:
                List<Integer> n = number.getResult();
                List<Integer> m = money.getResult();
                List<Integer> t = type.getResult();
                mslm.taskType = 2;
                if (!t.isEmpty()) {
                    mslm.redPacketType = t.toString().substring(1, t.toString().length() - 1).replaceAll(" ", "");
                } else {
                    mslm.redPacketType = "";
                }
                if (!n.isEmpty()) {
                    mslm.number = n.toString().substring(1, n.toString().length() - 1).replaceAll(" ", "");
                } else {
                    mslm.number = "";
                }
                if (!m.isEmpty()) {
                    mslm.amount = m.toString().substring(1, m.toString().length() - 1).replaceAll(" ", "");
                } else {
                    mslm.amount = "";
                }
                location(mslm);
                resultFilter.getChildAt(1).setSelected(false);
                popupWindow.dismiss();
                break;
        }
    }

    private void selectOne(View view) {
        if (curSelectedOrder == null) {
            view.setSelected(true);
        } else {
            view.setSelected(true);
            curSelectedOrder.setSelected(false);
        }
        curSelectedOrder = (TextView) view;
    }

    @Override
    public void onItemClick(MapDiscoverBottomSheetAdapter.ViewHolder viewHolder, int position) {
        MapUtils.moveToSpan(aMap, Double.valueOf(recyclerData.get(position).pubLatitude), Double.valueOf(recyclerData.get(position).pubLongitude));
        Marker marker = poiOverlay.findMarkerByPosition(position);
        marker.showInfoWindow();
        //设置banner图片
        initBanner(recyclerData.get(position));
        //设置details数据

        //显示
        if (detailsBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            detailsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void initBanner(MapRedResultModels.MapRedResultModel data) {
        List<String> images = new ArrayList<>();
        if (data.image1Url != null) images.add(data.image1Url);
        if (data.image2Url != null) images.add(data.image2Url);
        if (data.image3Url != null) images.add(data.image3Url);
        if (data.image4Url != null) images.add(data.image4Url);
        if (data.image5Url != null) images.add(data.image5Url);
        if (data.image6Url != null) images.add(data.image6Url);
        detailsBanner.setImages(images);
        detailsBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        detailsBanner.setImageLoader(new GlideImageLoader());
        detailsBanner.setBannerAnimation(Transformer.Default);
        detailsBanner.isAutoPlay(true);
        detailsBanner.setDelayTime(8000);
        detailsBanner.setIndicatorGravity(BannerConfig.CENTER);
        detailsBanner.start();
    }

    @Override
    public void onClick(final RedPacketDialog dialog) {
        DialogUtils.showConfirm(this,
                "温馨提示",
                "观看视频广告能提升大红包的概率哦！！！",
                "立即观看",
                "忍心拒绝",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(MapDiscoverActivity.this, VideoThreeActivity.class));
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openRedPacket();

                    }
                });
    }
    //        dialog.setDataBeforeStart(images, titles);
//        dialog.startAnimation();
//
//        dialog = new RedPacketDialog(this);
//        dialog.setOnOpenListener(this);
//        dialog.show();

    private void openRedPacket() {
        ToastUtils.Toast(MapDiscoverActivity.this, "开始模拟网络请求");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ToastUtils.Toast(MapDiscoverActivity.this, "结束模拟网络请求");
        onLoadFinish(null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        ToastUtils.Toast(this, marker.getId());
        redPacketBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        return false;
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && redPacketBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED && detailsBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            redPacketBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            popupWindow.dismiss();
        }
    }

    public static class LocationHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        public LocationHandler(Activity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MapSearchLocationModel m = msg.getData().getParcelable("info");
            Double longitude = msg.getData().getDouble("longitude");
            Double latitude = msg.getData().getDouble("latitude");
            if (m != null && m.taskType != 1) {
                new MapSearchTask(mActivityReference.get(), mActivityReference.get()).setClass(longitude + "", latitude + "", m.orderType, m.redPacketType, m.number, m.amount).request();
            }
        }
    }
}
