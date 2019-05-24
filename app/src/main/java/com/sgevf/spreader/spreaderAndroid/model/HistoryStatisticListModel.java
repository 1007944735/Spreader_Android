package com.sgevf.spreader.spreaderAndroid.model;

import java.util.List;

public class HistoryStatisticListModel {
    public String money;
    public String num;
    public String reNum;
    public String reMoney;
    public List<HistoryStatisticModel> list;

    public static class HistoryStatisticModel{
        public String name;
        public String time;
        public String account;
        public int id;
    }
}
