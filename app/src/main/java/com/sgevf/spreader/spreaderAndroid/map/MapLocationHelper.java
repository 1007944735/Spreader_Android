package com.sgevf.spreader.spreaderAndroid.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class MapLocationHelper {
    public AMapLocationClient mLocationClient;
    //定位回调监听
    public AMapLocationListener mLocationListener;

    public AMapLocationClientOption mLocationClientOption;

    public MapLocationHelper(Context context) {
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(mLocationListener);
        initOption();
    }

    private void initOption() {
        mLocationClientOption = new AMapLocationClientOption();
        //使用高精度
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //返回地址信息
        mLocationClientOption.setNeedAddress(true);
        //不允许模拟位置信息
        mLocationClientOption.setMockEnable(false);
        mLocationClientOption.setHttpTimeOut(3000);
        mLocationClient.setLocationOption(mLocationClientOption);
    }

    //一次定位
    public void startOnceLocation() {
        mLocationClientOption.setOnceLocation(true);
        mLocationClientOption.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();
    }

    //连续定位
    public void startLocation() {
        mLocationClient.startLocation();
    }

    public void setLocationListener(final LocationListener listener) {
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        listener.onLocationChange(aMapLocation);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AMapError", "locationCallBack Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
    }

    public interface LocationListener {
        void onLocationChange(AMapLocation location);
    }

    /**
     * 停止服务，本地服务不会停止
     */
    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    /**
     * 销毁定位客户端
     */
    public void destroyLocation() {
        mLocationClient.onDestroy();
    }
}
