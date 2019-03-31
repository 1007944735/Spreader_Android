package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MapSearchLocationModel implements Parcelable {
    public int taskType; //1 不发送信息 2 发送MapSearchTask 3 发送MapSearchOrderTsak 4 发送MapSearchFilterTask
    public String type;
    public String number;
    public String amount;

    public MapSearchLocationModel(){

    }
    protected MapSearchLocationModel(Parcel in) {
        taskType = in.readInt();
        type = in.readString();
        number = in.readString();
        amount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(taskType);
        dest.writeString(type);
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
