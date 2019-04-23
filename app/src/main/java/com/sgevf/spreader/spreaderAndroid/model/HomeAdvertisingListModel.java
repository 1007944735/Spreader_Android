package com.sgevf.spreader.spreaderAndroid.model;

import java.util.List;

public class HomeAdvertisingListModel {
    public List<HomeAdvertingModel> list;

    public class HomeAdvertingModel{
        public Integer id;
        public Double amount;
        public String type;
        public String pubTime;
        public String pubLongitude;
        public String pubLatitude;
        public String startTime;
        public String endTime;
        public Integer maxNumber;
        public String pubAddress;
        public String title;
        public String info;
        public String videoUrl;
        public String image1Url;
        public String image2Url;
        public String image3Url;
        public String image4Url;
        public String image5Url;
        public String image6Url;
    }
}
