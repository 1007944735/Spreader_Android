package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpandPhotoModel implements Parcelable {
    public String path;

    public ExpandPhotoModel() {

    }

    protected ExpandPhotoModel(Parcel in) {
        path = in.readString();
    }

    public static final Creator<ExpandPhotoModel> CREATOR = new Creator<ExpandPhotoModel>() {
        @Override
        public ExpandPhotoModel createFromParcel(Parcel in) {
            return new ExpandPhotoModel(in);
        }

        @Override
        public ExpandPhotoModel[] newArray(int size) {
            return new ExpandPhotoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
    }
}
