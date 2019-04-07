package com.sgevf.spreader.spreaderAndroid.map.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.sgevf.spreader.spreaderAndroid.R;

import java.util.ArrayList;
import java.util.List;

public class RouteOverlay {
    protected List<Polyline> allPolylines = new ArrayList<>();
    protected Marker startMarker;
    protected Marker endMarker;
    protected LatLng startPoint;
    protected LatLng endPoint;
    protected AMap aMap;
    private Context context;
    private Bitmap startBit;
    private Bitmap endBit;

    public RouteOverlay(Context context) {
        this.context = context;
    }

    public void removeFromMap() {
        if (startMarker != null) {
            startMarker.remove();
        }
        if (endMarker != null) {
            endMarker.remove();
        }
        for (Polyline polyline : allPolylines) {
            polyline.remove();
        }

        destroyBit();
    }

    private void destroyBit() {
        if (startBit != null) {
            startBit.recycle();
            startBit = null;
        }
        if (endBit != null) {
            endBit.recycle();
            endBit = null;
        }
    }

    protected BitmapDescriptor getStartBitmapDescriper() {
        return BitmapDescriptorFactory.fromResource(R.mipmap.icon_map_start);
    }

    protected BitmapDescriptor getEndBitmapDescriper() {
        return BitmapDescriptorFactory.fromResource(R.mipmap.icon_map_end);
    }

    protected void addStartAndEndMarker() {
        startMarker = aMap.addMarker(new MarkerOptions().icon(getStartBitmapDescriper()).position(startPoint));
        endMarker = aMap.addMarker(new MarkerOptions().icon(getEndBitmapDescriper()).position(endPoint));
    }

    /**
     * 移动当前镜头
     */
    public void zoomToSpan() {
        if (startPoint != null) {
            if (aMap == null)
                return;
            try {
                LatLngBounds bounds = getLatLngBounds();
                aMap.animateCamera(CameraUpdateFactory
                        .newLatLngBounds(bounds, 100));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    protected LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        b.include(new LatLng(startPoint.latitude, startPoint.longitude));
        b.include(new LatLng(endPoint.latitude, endPoint.longitude));
        for (Polyline polyline : allPolylines) {
            for (LatLng point : polyline.getPoints()) {
                b.include(point);
            }
        }
        return b.build();
    }

    protected void addPolyline(PolylineOptions options) {
        if (options == null) {
            return;
        }
        Polyline polyline = aMap.addPolyline(options);
        if (polyline != null) {
            allPolylines.add(polyline);
        }

    }

    protected int getPolylineColor() {
        return Color.parseColor("#0fc4b2");
    }

    protected int getPolylineWidth() {
        return 10;
    }


}
