package com.sgevf.spreader.spreaderAndroid.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.sgevf.spreader.http.utils.ToastUtils;

import static com.amap.api.services.route.RouteSearch.DRIVING_SINGLE_DEFAULT;

/**
 * 路径规划
 */
public class MapPathPlanHelper implements RouteSearch.OnRouteSearchListener {
    private Context context;
    private RouteSearch routeSearch;
    private MapPathPlanListener listener;

    public MapPathPlanHelper(Context context) {
        this.context = context;
    }

    /**
     * 步行
     *
     * @param fromLongitude
     * @param fromLatitude
     * @param toLongitude
     * @param toLatitude
     */
    public void walkPathPlan(double fromLongitude, double fromLatitude, double toLongitude, double toLatitude) {
        LatLonPoint from = new LatLonPoint(fromLatitude, fromLongitude);
        LatLonPoint to = new LatLonPoint(toLatitude, toLongitude);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);
        routeSearch = new RouteSearch(context);
        routeSearch.setRouteSearchListener(this);
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
        routeSearch.calculateWalkRouteAsyn(query);
    }

    /**
     * 自驾
     *
     * @param fromLongitude
     * @param fromLatitude
     * @param toLongitude
     * @param toLatitude
     */
    public void drivingPathPlan(double fromLongitude, double fromLatitude, double toLongitude, double toLatitude) {
        LatLonPoint from = new LatLonPoint(fromLatitude, fromLongitude);
        LatLonPoint to = new LatLonPoint(toLatitude, toLongitude);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);
        routeSearch = new RouteSearch(context);
        routeSearch.setRouteSearchListener(this);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, DRIVING_SINGLE_DEFAULT, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);
    }

    /**
     * 公交
     *
     * @param fromLongitude
     * @param fromLatitude
     * @param toLongitude
     * @param toLatitude
     * @param city
     */
    public void busPathPlan(double fromLongitude, double fromLatitude, double toLongitude, double toLatitude, String city) {
        LatLonPoint from = new LatLonPoint(fromLatitude, fromLongitude);
        LatLonPoint to = new LatLonPoint(toLatitude, toLongitude);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);
        routeSearch = new RouteSearch(context);
        routeSearch.setRouteSearchListener(this);
        RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, RouteSearch.BUS_LEASE_WALK, city, 0);
        routeSearch.calculateBusRouteAsyn(query);
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
        if (listener != null) {
            listener.busRoutePlan(busRouteResult, i);
        }
        if (i != AMapException.CODE_AMAP_SUCCESS) {
            Log.d("MapPathPlanHelper", "路径规划公交异常：" + i);
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (listener != null) {
            listener.drivingRoutePlan(driveRouteResult, i);
        }
        if (i != AMapException.CODE_AMAP_SUCCESS) {
            Log.d("MapPathPlanHelper", "路径规划自驾异常：" + i);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        if (listener != null) {
            listener.walkRoutePlan(walkRouteResult, i);
        }
        if (i != AMapException.CODE_AMAP_SUCCESS) {
            Log.d("MapPathPlanHelper", "路径规划步行异常：" + i);
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    public void setMapPathPlanListener(MapPathPlanListener listener) {
        this.listener = listener;
    }

    interface MapPathPlanListener {
        void walkRoutePlan(WalkRouteResult result, int i);

        void drivingRoutePlan(DriveRouteResult result, int i);

        void busRoutePlan(BusRouteResult result, int i);
    }
}
