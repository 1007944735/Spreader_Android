package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HomeAdvertisingListModel implements Parcelable {
    public List<HomeAdvertingModel> list;

    protected HomeAdvertisingListModel(Parcel in) {
    }

    public static final Creator<HomeAdvertisingListModel> CREATOR = new Creator<HomeAdvertisingListModel>() {
        @Override
        public HomeAdvertisingListModel createFromParcel(Parcel in) {
            return new HomeAdvertisingListModel(in);
        }

        @Override
        public HomeAdvertisingListModel[] newArray(int size) {
            return new HomeAdvertisingListModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class HomeAdvertingModel implements Parcelable {
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

        protected HomeAdvertingModel(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            if (in.readByte() == 0) {
                amount = null;
            } else {
                amount = in.readDouble();
            }
            type = in.readString();
            pubTime = in.readString();
            pubLongitude = in.readString();
            pubLatitude = in.readString();
            startTime = in.readString();
            endTime = in.readString();
            if (in.readByte() == 0) {
                maxNumber = null;
            } else {
                maxNumber = in.readInt();
            }
            pubAddress = in.readString();
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

        public static final Creator<HomeAdvertingModel> CREATOR = new Creator<HomeAdvertingModel>() {
            @Override
            public HomeAdvertingModel createFromParcel(Parcel in) {
                return new HomeAdvertingModel(in);
            }

            @Override
            public HomeAdvertingModel[] newArray(int size) {
                return new HomeAdvertingModel[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (id == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(id);
            }
            if (amount == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeDouble(amount);
            }
            parcel.writeString(type);
            parcel.writeString(pubTime);
            parcel.writeString(pubLongitude);
            parcel.writeString(pubLatitude);
            parcel.writeString(startTime);
            parcel.writeString(endTime);
            if (maxNumber == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(maxNumber);
            }
            parcel.writeString(pubAddress);
            parcel.writeString(title);
            parcel.writeString(info);
            parcel.writeString(videoUrl);
            parcel.writeString(image1Url);
            parcel.writeString(image2Url);
            parcel.writeString(image3Url);
            parcel.writeString(image4Url);
            parcel.writeString(image5Url);
            parcel.writeString(image6Url);
        }
    }
}
