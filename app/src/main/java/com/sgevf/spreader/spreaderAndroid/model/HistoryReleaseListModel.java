package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

public class HistoryReleaseListModel implements Parcelable{
    public List<HistoryReleaseModel> list;

    protected HistoryReleaseListModel(Parcel in) {
        list = in.createTypedArrayList(HistoryReleaseModel.CREATOR);
    }

    public static final Creator<HistoryReleaseListModel> CREATOR = new Creator<HistoryReleaseListModel>() {
        @Override
        public HistoryReleaseListModel createFromParcel(Parcel in) {
            return new HistoryReleaseListModel(in);
        }

        @Override
        public HistoryReleaseListModel[] newArray(int size) {
            return new HistoryReleaseListModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }


    public static class HistoryReleaseModel implements Parcelable {
        public int id;
        public String amount;
        public String type;
        public String pubTime;
        public String pubLongitude;
        public String pubLatitude;
        public String startTime;
        public String endTime;
        public String maxNumber;
        public String pubAddress;
        public String activiting;

        public String orderNo;
        public String createTime;
        public String status;

        public String title;
        public String info;
        public String videoUrl;
        public String image1Url;
        public String image2Url;
        public String image3Url;
        public String image4Url;
        public String image5Url;
        public String image6Url;

        public HistoryReleaseModel(JSONObject obj) {
            id = obj.optInt("id");
            amount = obj.optString("amount");
            type = obj.optString("type");
            pubTime = obj.optString("pubTime");
            pubLongitude = obj.optString("pubLongitude");
            pubLatitude = obj.optString("pubLatitude");
            startTime = obj.optString("startTime");
            endTime = obj.optString("endTime");
            maxNumber = obj.optString("maxNumber");
            pubAddress = obj.optString("pubAddress");
            activiting = obj.optString("activiting");

            orderNo = obj.optString("orderNo");
            createTime = obj.optString("createTime");
            status = obj.optString("status");

            title = obj.optString("title");
            info = obj.optString("info");
            videoUrl = obj.optString("videoUrl");
            image1Url = obj.optString("image1Url");
            image2Url = obj.optString("image2Url");
            image3Url = obj.optString("image3Url");
            image4Url = obj.optString("image4Url");
            image5Url = obj.optString("image5Url");
            image6Url = obj.optString("image6Url");
        }

        protected HistoryReleaseModel(Parcel in) {
            id = in.readInt();
            amount = in.readString();
            type = in.readString();
            pubTime = in.readString();
            pubLongitude = in.readString();
            pubLatitude = in.readString();
            startTime = in.readString();
            endTime = in.readString();
            maxNumber = in.readString();
            pubAddress = in.readString();
            activiting = in.readString();
            orderNo = in.readString();
            createTime = in.readString();
            status = in.readString();
            title = in.readString();
            info = in.readString();
            videoUrl = in.readString();
            image1Url = in.readString();
            image2Url = in.readString();
            image3Url = in.readString();
            image4Url = in.readString();
            image5Url = in.readString();
            image6Url = in.readString();
        }

        public static final Creator<HistoryReleaseModel> CREATOR = new Creator<HistoryReleaseModel>() {
            @Override
            public HistoryReleaseModel createFromParcel(Parcel in) {
                return new HistoryReleaseModel(in);
            }

            @Override
            public HistoryReleaseModel[] newArray(int size) {
                return new HistoryReleaseModel[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(amount);
            dest.writeString(type);
            dest.writeString(pubTime);
            dest.writeString(pubLongitude);
            dest.writeString(pubLatitude);
            dest.writeString(startTime);
            dest.writeString(endTime);
            dest.writeString(maxNumber);
            dest.writeString(pubAddress);
            dest.writeString(activiting);
            dest.writeString(orderNo);
            dest.writeString(createTime);
            dest.writeString(status);
            dest.writeString(title);
            dest.writeString(info);
            dest.writeString(videoUrl);
            dest.writeString(image1Url);
            dest.writeString(image2Url);
            dest.writeString(image3Url);
            dest.writeString(image4Url);
            dest.writeString(image5Url);
            dest.writeString(image6Url);
        }
    }
}
