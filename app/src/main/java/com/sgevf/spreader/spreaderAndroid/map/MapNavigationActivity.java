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
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.autonavi.amap.mapcore.Inner_3dMap_location;
import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.MapRoutesPathAdapter;
import com.sgevf.spreader.spreaderAndroid.map.overlay.DrivingRouteOverlay;
import com.sgevf.spreader.spreaderAndroid.map.overlay.WalkRouteOverlay;
import com.sgevf.spreader.spreaderAndroid.model.RedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.RedPacketDetailsTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.WindowHelper;

public class MapNavigationActivity extends BaseLoadingActivity<RedPacketDetailsModel> implements MapPathPlanHelper.MapPathPlanListener, AMap.OnMapLoadedListener, AMap.OnMyLocationChangeListener {
    private static final String WALK="walk";
    private static final String DRIVING="driving";
    private static final String BUS="bus";

    @BindView(R.id.aMap)
    MapView mapView;
    @BindView(R.id.routePlan)
    NestedScrollView routePlan;
    @BindView(R.id.result_tip)
    TextView resultTip;
    @BindView(R.id.routes)
    RecyclerView routes;

    AMap aMap;
    MyLocationStyle myLocationStyle;

    private int redPacketId;
    private Location location;
    private int tripWay;
    private MapPathPlanHelper helper;

    private WalkRouteOverlay walkRouteOverlay;
    private DrivingRouteOverlay drivingRouteOverlay;

    private MapRoutesPathAdapter adapter;

    private BottomSheetBehavior routePlanBeHavior;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_navigation);
        ButterKnife.bind(this);
        //创建地图
        init();
        initMap(savedInstanceState);
        initBeHavior();
    }

    private void init() {
        redPacketId = getIntent().getIntExtra("redPacketId", 0);
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
        aMap.getUiSettings().setZoomControlsEnabled(false);
        registerListener();

    }

    private void registerListener() {
        aMap.setOnMapLoadedListener(this);
        aMap.setOnMyLocationChangeListener(this);
    }

    private void initBeHavior() {
        routePlanBeHavior=BottomSheetBehavior.from(routePlan);
        routePlanBeHavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                if(view.getHeight()*v>= WindowHelper.getScreenHeight(MapNavigationActivity.this)*0.4){
                    ViewGroup.LayoutParams params = routePlan.getLayoutParams();
                    params.height = (int) (WindowHelper.getScreenHeight(MapNavigationActivity.this)*0.4);
                    routePlan.setLayoutParams(params);
                }
            }
        });
    }

    private void initRoutePlan(String type) {
        if(WALK.equals(type)){
            adapter=new MapRoutesPathAdapter<WalkStep>(this);
        }else if(DRIVING.equals(type)){
            adapter=new MapRoutesPathAdapter<DriveStep>(this);
        }else if(BUS.equals(type)){
            adapter=new MapRoutesPathAdapter<BusStep>(this);
        }
        routes.setLayoutManager(new LinearLayoutManager(this));
        routes.setAdapter(adapter);
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
        initRoutePlan(WALK);
        adapter.addData(result.getPaths().get(0).getSteps());
        aMap.clear();
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if(result!=null&&result.getPaths()!=null){
                if(result.getPaths().size()>0){
                    WalkPath walkPath=result.getPaths().get(0);
                    if(walkRouteOverlay!=null){
                        walkRouteOverlay.removeFromMap();
                    }

                    walkRouteOverlay=new WalkRouteOverlay(this,aMap,walkPath,result.getStartPos(),result.getTargetPos());
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();

                    adapter.addData(walkPath.getSteps());
                }
                routePlanBeHavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        } else {
            ToastUtils.Toast(this,"对不起，没有搜索到相关数据！");
        }
    }

    @Override
    public void drivingRoutePlan(DriveRouteResult result, int i) {
        initRoutePlan(DRIVING);
        adapter.addData(result.getPaths().get(0).getSteps());
        aMap.clear();
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if(result!=null&&result.getPaths()!=null){
                if(result.getPaths().size()>0){
                    DrivePath drivePath=result.getPaths().get(0);
                    if(drivingRouteOverlay!=null){
                        drivingRouteOverlay.removeFromMap();
                    }
                    drivingRouteOverlay=new DrivingRouteOverlay(this,aMap,drivePath,result.getStartPos(),result.getTargetPos());
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    adapter.addData(drivePath.getSteps());
                }
                routePlanBeHavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        } else {
            ToastUtils.Toast(this,"对不起，没有搜索到相关数据！");
        }
    }

    @Override
    public void busRoutePlan(BusRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {

        } else {
            ToastUtils.Toast(this,"对不起，没有搜索到相关数据！");
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
//        Log.d("TAG", "onMyLocationChange: " + location.toString());
        this.location=location;
    }

    @OnClick(R.id.back)
    public void back(){
        this.finish();
    }

    @OnClick(R.id.goTo)
    public void goToMapApp(){

    }
}
