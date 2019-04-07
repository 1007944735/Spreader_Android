package com.sgevf.spreader.spreaderAndroid.map.overlay;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.WalkStep;

import java.util.ArrayList;
import java.util.List;

public class DrivingRouteOverlay extends RouteOverlay {

    private DrivePath drivePath;

    private PolylineOptions polylineOptions;
    public DrivingRouteOverlay(Context context, AMap aMap, DrivePath path, LatLonPoint start,LatLonPoint end) {
        super(context);
        this.aMap=aMap;
        this.drivePath=path;
        this.startPoint=new LatLng(start.getLatitude(),start.getLongitude());
        this.endPoint=new LatLng(end.getLatitude(),end.getLongitude());
    }

    public void addToMap(){
        initPolylineOptions();
        polylineOptions.add(startPoint);
        List<DriveStep> drivePaths = drivePath.getSteps();
        for (int i = 0; i < drivePaths.size(); i++) {
            DriveStep driveStep = drivePaths.get(i);
//            LatLng latLng=new LatLng(walkStep.getPolyline().get(0).getLatitude(),walkStep.getPolyline().get(0).getLongitude());
            addWalkPolyLines(driveStep);
        }
        polylineOptions.add(endPoint);
        addStartAndEndMarker();
        addPolyline(polylineOptions);
    }

    private void addWalkPolyLines(DriveStep driveStep) {
        List<LatLng> latLngs = new ArrayList<>();
        for (LatLonPoint point : driveStep.getPolyline()) {
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
