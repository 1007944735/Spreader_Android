package com.sgevf.spreader.spreaderAndroid.map.overlay;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkStep;

import java.util.ArrayList;
import java.util.List;

public class WalkRouteOverlay extends RouteOverlay {

    private WalkPath walkPath;

    private PolylineOptions polylineOptions;

    public WalkRouteOverlay(Context context, AMap aMap, WalkPath path, LatLonPoint start, LatLonPoint end) {
        super(context);
        this.aMap = aMap;
        this.walkPath = path;
        this.startPoint = new LatLng(start.getLatitude(), start.getLongitude());
        this.endPoint = new LatLng(end.getLatitude(), end.getLongitude());
    }

    public void addToMap() {
        initPolylineOptions();
        polylineOptions.add(startPoint);
        List<WalkStep> walkSteps = walkPath.getSteps();
        for (int i = 0; i < walkSteps.size(); i++) {
            WalkStep walkStep = walkSteps.get(i);
//            LatLng latLng=new LatLng(walkStep.getPolyline().get(0).getLatitude(),walkStep.getPolyline().get(0).getLongitude());
            addWalkPolyLines(walkStep);
        }
        polylineOptions.add(endPoint);
        addStartAndEndMarker();
        addPolyline(polylineOptions);

    }

    private void addWalkPolyLines(WalkStep walkStep) {
        List<LatLng> latLngs = new ArrayList<>();
        for (LatLonPoint point : walkStep.getPolyline()) {
            latLngs.add(new LatLng(point.getLatitude(), point.getLongitude()));
        }
        polylineOptions.addAll(latLngs);
    }

    private void initPolylineOptions() {
        polylineOptions = null;
        polylineOptions = new PolylineOptions();
        polylineOptions.color(getPolylineColor()).width(getPolylineWidth());
    }
}
