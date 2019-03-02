package com.sgevf.spreader.spreaderAndroid.map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.wuxl.pathanimation.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MapPoiSearch {
    private Context context;
    private OnKeyFinishListener keyFinishListener;
    private OnAutoTipsFinishListener autoTipsFinishListener;

    private PoiItem item;

    public MapPoiSearch(Context context) {
        this.context = context;
    }

    /**
     * 关键字搜索
     *
     * @param keyWord
     * @param poi
     * @param cityCode
     * @param page
     * @return
     */
    public MapPoiSearch searchKeyPoi(String keyWord, String poi, String cityCode, int page) {
        searchKeyPoi(keyWord, poi, cityCode, 10, page, null);
        return this;
    }

    public MapPoiSearch searchKeyPoi(String keyWord, String poi, String cityCode, int size, int page) {
        searchKeyPoi(keyWord, poi, cityCode, size, page, null);
        return this;
    }

    public MapPoiSearch searchKeyPoi(final String keyWord, final String poi, final String cityCode, final int size, final int page, final PoiSearch.SearchBound bound) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, poi, cityCode);
        query.setPageSize(size);
        query.setPageNum(page);
        PoiSearch poiSearch = new PoiSearch(context, query);
        if (bound != null) {
            poiSearch.setBound(bound);
        }
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                if (i == 1000) {
                    if (poiResult != null && poiResult.getQuery() != null) {
                        ArrayList<PoiItem> list = poiResult.getPois();
//                        if (item != null) {
//                            list.add(0, item);
//                        }
                        if (list.size() == 0) {
                            searchKeyPoi(keyWord, poi, "", size, page, bound);
                        } else {
                            keyFinishListener.finish(list);
                        }
                    } else {
                        Toast.makeText(context, "无搜索结果", Toast.LENGTH_SHORT).show();
                        searchKeyPoi(keyWord, poi, "", size, page, bound);
                    }
                } else {
                    Toast.makeText(context, "无搜索结果", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
        return this;
    }

    public MapPoiSearch setKeyFinishListener(OnKeyFinishListener keyFinishListener) {
        this.keyFinishListener = keyFinishListener;
        return this;
    }

    public interface OnKeyFinishListener {
        void finish(List<PoiItem> pois);
    }

    /**
     * 搜索内容自动提示
     *
     * @param keyWord
     * @return
     */
    public MapPoiSearch searchAutoTips(String keyWord) {
        searchAutoTips(keyWord, UserConfig.getAdCode(context));
        return this;
    }

    public MapPoiSearch searchAutoTips(String keyWord, String city) {
        InputtipsQuery inputquery = new InputtipsQuery(keyWord, city);
//        inputquery.setCityLimit(true); //限制在当前城市
        Inputtips inputtips = new Inputtips(context, inputquery);
        inputtips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int i) {
                if (i == 1000 && autoTipsFinishListener != null) {
                    autoTipsFinishListener.finish(list);
                } else {
                    Toast.makeText(context, "无搜索结果", Toast.LENGTH_SHORT).show();
                }
            }
        });
        inputtips.requestInputtipsAsyn();
        return this;
    }

    public MapPoiSearch setOnAutoTipsFinishListener(OnAutoTipsFinishListener finishListener) {
        this.autoTipsFinishListener = finishListener;
        return this;
    }

    public interface OnAutoTipsFinishListener {
        void finish(List<Tip> list);
    }

    /**
     * 搜索周边的地址，调用searchKeyPoi
     *
     * @param point
     * @param range
     * @return
     */
    public MapPoiSearch searchGeocoder(final LatLonPoint point, float range) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(context);
        RegeocodeQuery query = new RegeocodeQuery(point, range, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS) {
                    if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                            && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                        String address = regeocodeResult.getRegeocodeAddress().getProvince() + regeocodeResult.getRegeocodeAddress().getCity() + regeocodeResult.getRegeocodeAddress().getDistrict() + regeocodeResult.getRegeocodeAddress().getTownship();
                        item = new PoiItem("regeo", point, address, address);
                        searchKeyPoi("", "", UserConfig.getAdCode(context), 10, 0, new PoiSearch.SearchBound(point, 1000, true));
                    }
                } else {
                    Toast.makeText(context, "error code is " + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        return this;
    }

}
