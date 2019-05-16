package com.sgevf.spreader.spreaderAndroid.map;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.autonavi.amap.mapcore.Inner_3dMap_location;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.MapDiscoverBottomSheetAdapter;
import com.sgevf.spreader.spreaderAndroid.adapter.MapDiscoverCouponAdapter;
import com.sgevf.spreader.spreaderAndroid.adapter.RedPacketCouponAdapter;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.map.overlay.PoiOverlay;
import com.sgevf.spreader.spreaderAndroid.model.CardListModel;
import com.sgevf.spreader.spreaderAndroid.model.GrabRedPacketModel;
import com.sgevf.spreader.spreaderAndroid.model.HomeAdvertisingListModel;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;
import com.sgevf.spreader.spreaderAndroid.model.MapSearchLocationModel;
import com.sgevf.spreader.spreaderAndroid.model.RedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.GrabRedPacketTask;
import com.sgevf.spreader.spreaderAndroid.task.MapSearchTask;
import com.sgevf.spreader.spreaderAndroid.task.RedPacketDetailsTask;
import com.sgevf.spreader.spreaderAndroid.view.FilterOptionView;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;
import com.sgevf.spreader.spreaderAndroid.view.RedPacketDialog;
import com.sgevf.spreader.spreaderAndroid.view.SuperListView;
import com.sgevf.spreader.spreaderAndroid.view.VideoBannerViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.DateUtils;
import utils.MapUtils;
import utils.WindowHelper;

public class MapDiscoverActivity extends BaseLoadingActivity<MapRedResultModels> implements View.OnClickListener, MapDiscoverBottomSheetAdapter.OnItemClickListener, AMap.OnMarkerClickListener, AMap.OnMapTouchListener, MapPathPlanHelper.MapPathPlanListener {
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
    @BindView(R.id.videoView)
    ViewPager videoView;
    @BindView(R.id.details_layout)
    LinearLayout detailsLayout;

    @BindView(R.id.sponsor_image)
    ImageView sponsorImage;
    @BindView(R.id.sponsor_name)
    TextView sponserName;
    @BindView(R.id.headline)
    TextView headline;
    @BindView(R.id.describe)
    TextView describe;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.remaining_time)
    TextView remainingTime;
    @BindView(R.id.red_packet_info)
    TextView redPacketInfo;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.walk)
    TextView walk;
    @BindView(R.id.driving)
    TextView driving;
    @BindView(R.id.bus)
    TextView bus;
    @BindView(R.id.requestLoading)
    ProgressBar requestLoading;
    @BindView(R.id.open)
    Button open;
    @BindView(R.id.coupon)
    SuperListView coupon;
    @BindView(R.id.discount)
    LinearLayout discount;

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
    private MapSearchLocationModel mslm;
    private List<MapRedResultModels.MapRedResultModel> recyclerData;
    private boolean loading = false;
    private int clickPositionId;
    private Timer timer;
    private MapPathPlanHelper pathHelper;
    private Location curLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_discover);
        ButterKnife.bind(this);
        new HeaderView(this).setToolbarBackground(android.R.color.transparent);
        init();
        //创建地图
        initMap(savedInstanceState);
        initSetting();
        initRecyclerView();
        initDetails();
        initResultFilter();
        //从HomeFragment跳转过来的
        initFromHome();
    }

    private void init() {
        pathHelper = new MapPathPlanHelper(this);
        pathHelper.setMapPathPlanListener(this);
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
        ViewGroup.LayoutParams dp = videoView.getLayoutParams();
        detailsBannerHeight = (int) (screenHeight * 0.3);
        dp.height = detailsBannerHeight;
        videoView.setLayoutParams(dp);
        videoView.setTranslationY(-dp.height);

        ViewGroup.LayoutParams dlp = details.getLayoutParams();
        dlp.height = screenHeight - detailsBannerHeight;
        details.setLayoutParams(dlp);

        detailsBehavior = BottomSheetBehavior.from(details);
        detailsBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_EXPANDED && loading) {
                    new RedPacketDetailsTask(MapDiscoverActivity.this, MapDiscoverActivity.this).setClass(clickPositionId, curLocation.getLongitude() + "", curLocation.getLatitude() + "").request();
                    loading = false;
                    requestLoading.setVisibility(View.VISIBLE);
                } else if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    detailsLayout.setVisibility(View.GONE);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                videoView.setTranslationY(-(1 - v) * detailsBannerHeight);
                videoView.setAlpha(v);
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

    private void initFromHome() {
        HomeAdvertisingListModel.HomeAdvertingModel model = getIntent().getParcelableExtra("model");
        if (model != null) {
            initBanner(changeType(model));
            loading = true;
            clickPositionId = model.id;
            //显示
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (detailsBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        detailsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
            }, 500);

        }
    }

    //从HomeAdvertisingListModel.HomeAdvertingModel转化到MapRedResultModels.MapRedResultModel
    private MapRedResultModels.MapRedResultModel changeType(HomeAdvertisingListModel.HomeAdvertingModel model) {
        MapRedResultModels.MapRedResultModel mrrm = new MapRedResultModels.MapRedResultModel();
        mrrm.id = model.id;
        mrrm.amount = model.amount;
        mrrm.type = model.type;
        mrrm.pubTime = model.pubTime;
        mrrm.pubLongitude = model.pubLongitude;
        mrrm.pubLatitude = model.pubLatitude;
        mrrm.startTime = model.startTime;
        mrrm.endTime = model.endTime;
        mrrm.maxNumber = model.maxNumber;
        mrrm.pubAddress = model.pubAddress;
        mrrm.title = model.title;
        mrrm.info = model.info;
        mrrm.videoUrl = model.videoUrl;
        mrrm.image1Url = model.image1Url;
        mrrm.image2Url = model.image2Url;
        mrrm.image3Url = model.image3Url;
        mrrm.image4Url = model.image4Url;
        mrrm.image5Url = model.image5Url;
        mrrm.image6Url = model.image6Url;
        return mrrm;
    }

    private void clearAllSelected() {
        for (int i = 0; i < resultFilter.getChildCount(); i++) {
            ((View) resultFilter.getChildAt(i)).setSelected(false);
        }
    }

    private void createPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_map_pop, null);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, redPackets.getHeight());
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
                curLocation = location;
                if (onlyOnce) {
                    MapUtils.moveToSpan(aMap, location.getLatitude(), location.getLongitude(), 18, 30, 0);
                    if (mslm == null) {
                        mslm = new MapSearchLocationModel();
                    }
                    new MapSearchTask(MapDiscoverActivity.this, MapDiscoverActivity.this).setClass(curLocation.getLongitude() + "", curLocation.getLatitude() + "", mslm.orderType, mslm.redPacketType, mslm.number, mslm.amount).request();
                    onlyOnce = false;
                }
            }
        });
    }

    @OnClick(R.id.location)
    public void location(View view) {
        //定位
        MapUtils.moveToSpan(aMap, curLocation.getLatitude(), curLocation.getLongitude(), 18, 30, 0);
    }

    @OnClick(R.id.open)
    public void open() {
        new GrabRedPacketTask(this, this).setClass(clickPositionId, curLocation.getLongitude() + "", curLocation.getLatitude() + "").request();
    }

    @OnClick(R.id.walk)
    public void walk() {
        //导航
        Intent intent = new Intent(this, MapNavigationActivity.class);
        intent.putExtra("redPacketId", clickPositionId);
//        intent.putExtra("location",curLocation);
        intent.putExtra("tripWay", 1);
        startActivity(intent);
    }

    @OnClick(R.id.driving)
    public void driving() {
        //导航
        Intent intent = new Intent(this, MapNavigationActivity.class);
        intent.putExtra("redPacketId", clickPositionId);
//        intent.putExtra("location",curLocation);
        intent.putExtra("tripWay", 2);
        startActivity(intent);
    }

    @OnClick(R.id.bus)
    public void bus() {
        //导航
        Intent intent = new Intent(this, MapNavigationActivity.class);
        intent.putExtra("redPacketId", clickPositionId);
//        intent.putExtra("location",curLocation);
        intent.putExtra("tripWay", 2);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        mapView.onResume();
        if (!onlyOnce) {
            new MapSearchTask(this, this).setClass(curLocation.getLongitude() + "", curLocation.getLatitude() + "", mslm.orderType, mslm.redPacketType, mslm.number, mslm.amount).request();
        }
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
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
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

    public void grabResult(GrabRedPacketModel model) {
        open.setEnabled(false);
        open.setText("已领取");
        dialog = new RedPacketDialog(this);
        dialog.show();
        dialog.setFromName(model.name);
        dialog.setMoney(model.money);
        dialog.setAdapter(new RedPacketCouponAdapter(this, model.list));
    }

    public void initDetailsLayout(RedPacketDetailsModel model) {
        //加载detailsLayout数据
        GlideManager.circleImage(this, model.sponserImage, sponsorImage);
        sponserName.setText(model.sponserName);
        headline.setText(model.title);
        describe.setText(model.info);
        address.setText(model.pubAddress);

        long et = DateUtils.reFormat(model.endTime);
        long ct = new Date().getTime();
        if ("1".equals(model.isGrab)) {
            open.setEnabled(false);
            open.setText("已领取");
            timer = new Timer(et - ct, 1000, true);
        } else {
            open.setEnabled(true);
            open.setText("领取");
            timer = new Timer(et - ct, 1000, false);
        }

        timer.setView(remainingTime, open);
        timer.start();

        redPacketInfo.setText(model.distance + "M | " + model.maxNumber + "人 | " + model.amount + "元");
        time.setText(model.startTime + "~" + model.endTime);
        pathHelper.walkPathPlan(curLocation.getLongitude(), curLocation.getLatitude(), Double.valueOf(model.pubLongitude), Double.valueOf(model.pubLatitude));
        pathHelper.drivingPathPlan(curLocation.getLongitude(), curLocation.getLatitude(), Double.valueOf(model.pubLongitude), Double.valueOf(model.pubLatitude));
        pathHelper.busPathPlan(curLocation.getLongitude(), curLocation.getLatitude(), Double.valueOf(model.pubLongitude), Double.valueOf(model.pubLatitude), ((Inner_3dMap_location) curLocation).getCityCode());
        requestLoading.setVisibility(View.GONE);
        initCoupon(model.list);
        //数据加载完才显示
        detailsLayout.setVisibility(View.VISIBLE);
    }

    private void initCoupon(List<CardListModel.CardManagerModel> datas) {
        if (datas != null && !datas.isEmpty()) {
            discount.setVisibility(View.VISIBLE);
            MapDiscoverCouponAdapter adapter = new MapDiscoverCouponAdapter(this, datas);
            coupon.setAdapter(adapter);
        } else {
            discount.setVisibility(View.GONE);
        }
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
                    .icon(BitmapFactory.decodeResource(getResources(),R.mipmap.icon_map_red_packet_coordinate))
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
                mslm.orderType = "1";
                new MapSearchTask(this, this).setClass(curLocation.getLongitude() + "", curLocation.getLatitude() + "", mslm.orderType, mslm.redPacketType, mslm.number, mslm.amount).request();
                resultFilter.getChildAt(0).setSelected(false);
                popupWindow.dismiss();
                break;
            case R.id.maxCount:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_max_count);
                selectOne(v);
                mslm.orderType = "2";
                new MapSearchTask(this, this).setClass(curLocation.getLongitude() + "", curLocation.getLatitude() + "", mslm.orderType, mslm.redPacketType, mslm.number, mslm.amount).request();
                resultFilter.getChildAt(0).setSelected(false);
                popupWindow.dismiss();
                break;
            case R.id.minDistance:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_min_distance);
                selectOne(v);
                mslm.orderType = "3";
                new MapSearchTask(this, this).setClass(curLocation.getLongitude() + "", curLocation.getLatitude() + "", mslm.orderType, mslm.redPacketType, mslm.number, mslm.amount).request();
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
                new MapSearchTask(this, this).setClass(curLocation.getLongitude() + "", curLocation.getLatitude() + "", mslm.orderType, mslm.redPacketType, mslm.number, mslm.amount).request();
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
        //设置banner图片
        initBanner(recyclerData.get(position));
        //设置loading为true，请求后台，加载detailsLayout数据
        loading = true;
        clickPositionId = recyclerData.get(position).id;
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

        VideoBannerViewAdapter adapter=new VideoBannerViewAdapter(this,data.videoUrl,images);
        videoView.setAdapter(adapter);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int p = poiOverlay.getPosition(marker);
        if (p != -1) {
            //设置banner图片
            initBanner(recyclerData.get(p));
            //设置loading为true，请求后台，加载detailsLayout数据
            loading = true;
            clickPositionId = recyclerData.get(p).id;
        }
        redPacketBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        detailsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        return false;
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && redPacketBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED && detailsBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            redPacketBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            popupWindow.dismiss();
        }
    }

    @Override
    public void walkRoutePlan(WalkRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            float d = 0;
            float t = 0;
            for (WalkStep step : result.getPaths().get(0).getSteps()) {
                d += step.getDistance();
                t += step.getDuration();
            }
            walk.setText(d + "米/约" + (int) t / 60 + "分");
            walk.setEnabled(true);
        } else {
            walk.setText("暂无");
            walk.setEnabled(false);
        }
    }

    @Override
    public void drivingRoutePlan(DriveRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            float d = 0;
            float t = 0;
            for (DriveStep step : result.getPaths().get(0).getSteps()) {
                d += step.getDistance();
                t += step.getDuration();
            }
            driving.setText(d + "米/约" + (int) t / 60 + "分");
            driving.setEnabled(true);
        } else {
            driving.setText("暂无");
            driving.setEnabled(false);
        }

    }

    @Override
    public void busRoutePlan(BusRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            float d = 0;
            float t = 0;
            if (result.getPaths().isEmpty()) {
                bus.setText("暂无");
                bus.setEnabled(false);
                return;
            }
            List<BusStep> steps = result.getPaths().get(0).getSteps();
            for (RouteBusLineItem step : steps.get(0).getBusLines()) {
                d += step.getDistance();
                t += step.getDuration();
            }
            bus.setText(d + "米/约" + (int) t / 60 + "分");
            bus.setEnabled(true);
        } else {
            bus.setText("暂无");
            bus.setEnabled(false);
        }
    }

    class Timer extends CountDownTimer {
        private TextView remainingTime;
        private Button open;
        private boolean isGrab;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Timer(long millisInFuture, long countDownInterval, boolean isGrab) {
            super(millisInFuture, countDownInterval);
            this.isGrab = isGrab;
        }

        public void setView(TextView view, Button open) {
            this.remainingTime = view;
            this.open = open;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (!isGrab && !open.isEnabled()) {
                open.setEnabled(true);
            }

            if (millisUntilFinished >= 0) {
                int day = (int) (millisUntilFinished / (1000 * 60 * 60 * 24));
                int hour = (int) (millisUntilFinished % (1000 * 60 * 60 * 24) / (1000 * 60 * 60));
                int minutes = (int) (millisUntilFinished % (1000 * 60 * 60 * 24) % (1000 * 60 * 60) / (1000 * 60));
                int seconds = (int) (millisUntilFinished % (1000 * 60 * 60 * 24) % (1000 * 60 * 60) % (1000 * 60) / 1000);
                if (remainingTime != null) {
                    if (day != 0) {
                        remainingTime.setText("剩余" + day + "天" + hour + "时" + minutes + "分" + seconds + "秒");
                    } else if (hour != 0) {
                        remainingTime.setText("剩余" + hour + "时" + minutes + "分" + seconds + "秒");
                    } else if (minutes != 0) {
                        remainingTime.setText("剩余" + minutes + "分" + seconds + "秒");
                    } else {
                        remainingTime.setText("剩余" + seconds + "秒");
                    }
                }
            }
        }

        @Override
        public void onFinish() {
            if (!isGrab) {
                remainingTime.setText("活动已结束");
                open.setEnabled(false);
                open.setText("领取");
            }
        }
    }
}
