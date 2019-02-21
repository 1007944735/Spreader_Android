package com.sgevf.spreader.spreaderAndroid.map;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

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
        if (mapMarkers == null) return;
        mapMarkers.add(options);
        Marker marker = aMap.addMarker(options);
        markers.add(marker);
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

    /**
     * 清除所有的marker
     */
    public void clearAllMarker() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }


}
