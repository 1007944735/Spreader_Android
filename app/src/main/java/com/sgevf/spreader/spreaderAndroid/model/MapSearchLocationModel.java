package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MapSearchLocationModel implements Parcelable {
    public String orderType; //1 人数最多，2 金额最大，3 距离最近
    public String redPacketType;//0 随机红包,1 固定红包
    public String number;
    public String amount;

    public MapSearchLocationModel() {
        this.orderType = "";
        this.redPacketType = "";
        this.number = "";
        this.amount = "";
    }

    protected MapSearchLocationModel(Parcel in) {
        orderType = in.readString();
        redPacketType = in.readString();
        number = in.readString();
        amount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderType);
        dest.writeString(redPacketType);
        dest.writeString(number);
        dest.writeString(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MapSearchLocationModel> CREATOR = new Creator<MapSearchLocationModel>() {
        @Override
        public MapSearchLocationModel createFromParcel(Parcel in) {
            return new MapSearchLocationModel(in);
        }

        @Override
        public MapSearchLocationModel[] newArray(int size) {
            return new MapSearchLocationModel[size];
        }
    };
}
