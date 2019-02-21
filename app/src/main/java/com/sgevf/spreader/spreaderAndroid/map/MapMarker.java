package com.sgevf.spreader.spreaderAndroid.map;

import android.graphics.Bitmap;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

public class MapMarker {
    private Builder builder;

    public MapMarker(Builder builder) {
        this.builder = builder;
    }

    public MarkerOptions create() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(builder.latitude, builder.longitude));
        markerOptions.title(builder.title);
        markerOptions.snippet(builder.snippet);
        markerOptions.draggable(builder.draggable);
        markerOptions.visible(builder.visible);
        markerOptions.anchor(builder.anchorU, builder.anchorV);
        markerOptions.alpha(builder.alpha);
        markerOptions.infoWindowEnable(builder.infoWindowEnable);
        if (builder.icon != null) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(builder.icon));
        }
        return markerOptions;
    }

    public double getLatitude() {
        return builder.latitude;
    }

    public double getLongitude() {
        return builder.longitude;
    }

    public String getTitle() {
        return builder.title;
    }

    public String getSnippet() {
        return builder.snippet;
    }

    public boolean isDraggable() {
        return builder.draggable;
    }

    public boolean isVisible() {
        return builder.visible;
    }

    public float getAnchorU() {
        return builder.anchorU;
    }

    public float getAnchorV() {
        return builder.anchorV;
    }

    public float getAlpha() {
        return builder.alpha;
    }

    public Bitmap getIcon() {
        return builder.icon;
    }

    public boolean isInfoWindowEnable() {
        return builder.infoWindowEnable;
    }

    public static class Builder {
        private double latitude = 0.0;//纬度
        private double longitude = 0.0;//经度

        private String title = "";//标题
        private String snippet = "";//点标记内容
        private boolean draggable = false;//点标记是否可以拖拽
        private boolean visible = true;//点标记是否可见
        private float anchorU = 0.5f;//点标记的锚点
        private float anchorV = 0.5f;
        private float alpha = 255f;//透明度
        private Bitmap icon = null;
        private boolean infoWindowEnable = false;//infoWindow是否可见

        public Builder position(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder snippet(String snippet) {
            this.snippet = snippet;
            return this;
        }

        public Builder draggable(boolean draggable) {
            this.draggable = draggable;
            return this;
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder anchor(float anchorU, float anchorV) {
            this.anchorU = anchorU;
            this.anchorV = anchorV;
            return this;
        }

        public Builder alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder icon(Bitmap icon) {
            this.icon = icon;
            return this;
        }

        public Builder infoWindowEnable(boolean enable) {
            this.infoWindowEnable = enable;
            return this;
        }

        public MarkerOptions build() {
            return new MapMarker(this).create();
        }

    }
}
