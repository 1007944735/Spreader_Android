package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpandVideoModel implements Parcelable {
    public String name;
    public String path;
    public int duration;
    public long createTime;

    public ExpandVideoModel() {
    }


    protected ExpandVideoModel(Parcel in) {

        name = in.readString();
        path = in.readString();
        duration = in.readInt();
        createTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeInt(duration);
        dest.writeLong(createTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExpandVideoModel> CREATOR = new Creator<ExpandVideoModel>() {
        @Override
        public ExpandVideoModel createFromParcel(Parcel in) {
            return new ExpandVideoModel(in);
        }

        @Override
        public ExpandVideoModel[] newArray(int size) {
            return new ExpandVideoModel[size];
        }
    };
}
