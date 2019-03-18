package utils;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;

public class MapUtils {
    /**
     * 移动镜头到当前视角
     *
     * @param aMap
     * @param latitude
     * @param longitude
     * @param v
     */
    public static void moveToSpan(AMap aMap, double latitude, double longitude, float v) {
        if (aMap == null) return;
        LatLng point = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, v));
    }

    /**
     * 移动镜头到当前视角
     *
     * @param aMap
     * @param latitude
     * @param longitude
     */
    public static void moveToSpan(AMap aMap, double latitude, double longitude) {
        if (aMap == null) return;
        LatLng point = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }

    public static void moveToSpan(AMap aMap, double latitude, double longitude, float v, float v1, float v2) {
        if (aMap == null) return;
        LatLng point = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(point, v, v1, v2)));
    }
}
