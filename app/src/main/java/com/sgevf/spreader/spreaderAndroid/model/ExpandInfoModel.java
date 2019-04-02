package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ExpandInfoModel implements Parcelable {
    public String title;
    public String info;
    public List<ExpandPhotoModel> pictures;
    public ExpandVideoModel video;

    public ExpandInfoModel() {

    }

    protected ExpandInfoModel(Parcel in) {
        title = in.readString();
        info = in.readString();
        pictures = in.createTypedArrayList(ExpandPhotoModel.CREATOR);
        video = in.readParcelable(ExpandVideoModel.class.getClassLoader());
    }

    public static final Creator<ExpandInfoModel> CREATOR = new Creator<ExpandInfoModel>() {
        @Override
        public ExpandInfoModel createFromParcel(Parcel in) {
            return new ExpandInfoModel(in);
        }

        @Override
        public ExpandInfoModel[] newArray(int size) {
            return new ExpandInfoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(info);
        dest.writeTypedList(pictures);
        dest.writeParcelable(video, flags);
    }
}
