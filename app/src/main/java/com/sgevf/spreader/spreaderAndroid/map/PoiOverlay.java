package com.sgevf.spreader.spreaderAndroid.map;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;

import java.util.ArrayList;
import java.util.List;

public class PoiOverlay {
    private List<MarkerOptions> mapMarkers;
    private List<Marker> markers = new ArrayList<>();
    private AMap aMap;

    public PoiOverlay(AMap aMap) {
        this.aMap = aMap;
        this.mapMarkers = new ArrayList<>();
    }

    public PoiOverlay(AMap aMap, List<MarkerOptions> mapMarkers) {
        this.aMap = aMap;
        this.mapMarkers = mapMarkers;
    }

    /**
     * 添加MarkerOptions，并向map添加marker
     *
     * @param options
     */
    public void addMapMarker(MarkerOptions options) {
//        if (mapMarkers == null) return;
//        mapMarkers.add(options);
        Marker marker = aMap.addMarker(options);
        markers.add(marker);
        addMarkerAnimation(marker);
    }

    /**
     * marker添加上浮效果
     *
     * @param marker
     */
    private void addMarkerAnimation(Marker marker) {
        Animation markerAnimation = new ScaleAnimation(0, 1, 0, 1);
        markerAnimation.setDuration(1000);
        marker.setAnimation(markerAnimation);
        marker.startAnimation();
    }

    /**
     * 向map中添加marker
     */
    public void addMarkerToMap() {
        if (mapMarkers == null || mapMarkers.size() <= 0) return;
        for (MarkerOptions options : mapMarkers) {
            Marker marker = aMap.addMarker(options);
            markers.add(marker);
        }
    }

    public void addMarkerToMap(List<MarkerOptions> options) {
        clearAllMarker();
        if (options == null || options.isEmpty()) return;
        for (MarkerOptions option : options) {
            Marker marker = aMap.addMarker(option);
            markers.add(marker);
        }
    }

    /**
     * 清除所有的marker
     */
    public void clearAllMarker() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }

    public Marker getFirstMarker() {
        if (markers != null && markers.size() > 0)
            return markers.get(0);
        return null;
    }


}
