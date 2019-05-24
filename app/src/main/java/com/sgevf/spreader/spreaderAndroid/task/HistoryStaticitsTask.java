package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.HistoryStatisticActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistoryStatisticListModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class HistoryStaticitsTask extends BaseService<PubService, HistoryStatisticListModel>{
    public HistoryStaticitsTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public HistoryStaticitsTask setClass(int id,String account,String maxNum){
        params.put("id",id+"");
        params.put("account",account);
        params.put("maxNum",maxNum);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.historyStatistic(data);
    }

    @Override
    public void onSuccess(HistoryStatisticListModel historyStatisticListModel) {
        if(mTarget instanceof HistoryStatisticActivity){
            ((HistoryStatisticActivity) mTarget).onLoadFinish(historyStatisticListModel);
        }
    }
}
