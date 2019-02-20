package com.sgevf.spreader.spreaderAndroid.map;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

public class MapPoiSearch {
    private Context context;
    private OnFinishListener finishListener;

    public MapPoiSearch(Context context) {
        this.context = context;
    }

    /**
     * 关键字搜索
     * @param keyWord
     * @param poi
     * @param cityCode
     * @param page
     * @return
     */
    public MapPoiSearch searchKeyPoi(String keyWord, String poi, String cityCode, int page) {
        searchKeyPoi(keyWord, poi, cityCode, 10, page);
        return this;
    }

    public MapPoiSearch searchKeyPoi(String keyWord, String poi, String cityCode, int size, int page) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, poi, cityCode);
        query.setPageSize(size);
        query.setPageNum(page);
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                poiResult.getPois();
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
        return this;
    }

    /**
     * 搜索内容自动提示
     * @param keyWord
     * @return
     */
    public MapPoiSearch searchAutoTips(String keyWord) {
        searchAutoTips(keyWord, "");
        return this;
    }

    public MapPoiSearch searchAutoTips(String keyWord, String city) {
        InputtipsQuery inputquery = new InputtipsQuery(keyWord, city);
        inputquery.setCityLimit(true); //限制在当前城市
        Inputtips inputtips = new Inputtips(context, inputquery);
        inputtips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int i) {
                if (i == 1000&&finishListener!=null) {
                    finishListener.finish(list);
                }
            }
        });
        inputtips.requestInputtipsAsyn();
        return this;
    }

    public MapPoiSearch setOnFinishListener(OnFinishListener finishListener){
        this.finishListener=finishListener;
        return this;
    }

    public interface OnFinishListener{
        void finish(List<Tip> list);
    }

}
