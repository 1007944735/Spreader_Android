package com.sgevf.spreader.spreaderAndroid.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.amap.api.maps.model.MyLocationStyle;
import com.sgevf.multimedia.video.VideoThreeActivity;
import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.MapDiscoverBottomSheetAdapter;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;
import com.sgevf.spreader.spreaderAndroid.task.MapSearchTask;
import com.sgevf.spreader.spreaderAndroid.view.FilterOptionView;
import com.sgevf.spreader.spreaderAndroid.view.RedPacketDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.DialogUtils;
import utils.MapUtils;
import utils.WindowHelper;

public class MapDiscoverActivity extends BaseLoadingActivity<MapRedResultModels> implements View.OnClickListener, MapDiscoverBottomSheetAdapter.OnItemClickListener, RedPacketDialog.OnOpenListener {
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
    private TextView curSelectedOrder;
    private FilterOptionView number;
    private FilterOptionView money;
    private FilterOptionView type;
    private String[] numberData;
    private String[] moneyData;
    private String[] typeData;
    private MapDiscoverBottomSheetAdapter adapter;
    private RedPacketDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_discover);
        ButterKnife.bind(this);
        //创建地图
        init();
        initMap(savedInstanceState);
        initSetting();
        initRecyclerView();
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

        bottomSheetBehavior = BottomSheetBehavior.from(scroll);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    resultTip.setVisibility(View.VISIBLE);
                } else {
                    resultTip.setVisibility(View.GONE);
                }
                if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    mapView.setTranslationY(-WindowHelper.getScreenHeight(MapDiscoverActivity.this) * 0.3f);
                    location(false);

                } else {
                    mapView.setTranslationY(0);
                    location(false);
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
                float distance;
                float secMax = view.getHeight() - bottomSheetBehavior.getPeekHeight() - resultFilter.getHeight();
                if (v >= 0) {
                    distance = (view.getHeight() - bottomSheetBehavior.getPeekHeight()) * v;
                } else {
                    distance = bottomSheetBehavior.getPeekHeight() * v;
                }
//                if (distance <= view.getHeight() * 0.4f && distance > 0) {
//                    mapView.setTranslationY(-distance);
//                } else if (distance < 0) {
//                    mapView.setTranslationY(0);
//                }
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

    private void clearAllSelected() {
        for (int i = 0; i < resultFilter.getChildCount(); i++) {
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
        location(false);
    }

    public void location(final boolean request){
        MapLocationHelper helper = new MapLocationHelper(this);
        helper.startOnceLocation();
        helper.setLocationListener(new MapLocationHelper.LocationListener() {
            @Override
            public void onLocationChange(AMapLocation location) {
                if(request){
                    new MapSearchTask(MapDiscoverActivity.this,MapDiscoverActivity.this).setClass(location.getLongitude()+"",location.getLatitude()+"").request();
                }
                MapUtils.moveToSpan(aMap, location.getLatitude(), location.getLongitude(), 18, 30, 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        mapView.onResume();
        location(true);
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
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLoadFinish(MapRedResultModels mapRedResultModels) {
//        List<String> images = new ArrayList<>();
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553570201&di=2428cac1e3c8977150dfd3d2d7fd5db9&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F68%2F59%2F71X58PICNjx_1024.jpg");
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552975482457&di=0324c062a084d72c198e9d71f7de1966&imgtype=0&src=http%3A%2F%2Fimg.juimg.com%2Ftuku%2Fyulantu%2F140218%2F330598-14021R23A410.jpg");
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552975482456&di=c4af7ee5fe832edb6956a4f01f920af5&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F40%2F15%2F83V58PICyKZ_1024.jpg");
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552975482456&di=b576cea863cf208421de60e9cde4cefe&imgtype=0&src=http%3A%2F%2Fsc.jb51.net%2Fuploads%2Fallimg%2F150403%2F10-1504031H411E6.jpg");
//
//        List<String> titles = new ArrayList<>();
//        titles.add("asda");
//        titles.add("asd");
//        titles.add("asdda");
//        titles.add("asasdda");
//        dialog.setDataBeforeStart(images, titles);
//        dialog.startAnimation();
        adapter.setData(mapRedResultModels.list);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smartOrder:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(titles[0]);
                selectOne(v);
                break;
            case R.id.maxPeople:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_max_people);
                selectOne(v);
                break;
            case R.id.maxCount:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_max_count);
                selectOne(v);
                break;
            case R.id.minDistance:
                ((TextView) resultFilter.getChildAt(0).findViewById(R.id.title)).setText(R.string.discover_order_min_distance);
                selectOne(v);
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
                ToastUtils.Toast(this, n.toString() + ":" + m.toString() + ":" + t.toString());
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
//        dialog = new RedPacketDialog(this);
//        dialog.setOnOpenListener(this);
//        dialog.show();
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
}
