package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

public class MapRedResultModels implements Parcelable {

    public List<MapRedResultModel> list;

    protected MapRedResultModels(Parcel in) {
        list = in.createTypedArrayList(MapRedResultModel.CREATOR);
    }

    public static final Creator<MapRedResultModels> CREATOR = new Creator<MapRedResultModels>() {
        @Override
        public MapRedResultModels createFromParcel(Parcel in) {
            return new MapRedResultModels(in);
        }

        @Override
        public MapRedResultModels[] newArray(int size) {
            return new MapRedResultModels[size];
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


    public static class MapRedResultModel implements Parcelable {
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
        public Double distance;
        public String title;
        public String info;
        public String videoUrl;
        public String image1Url;
        public String image2Url;
        public String image3Url;
        public String image4Url;
        public String image5Url;
        public String image6Url;

        public MapRedResultModel() {

        }

        public MapRedResultModel(JSONObject obj) {
            id = obj.optInt("id");
            amount = obj.optDouble("amount");
            type = obj.optString("type");
            pubTime = obj.optString("pubTime");
            pubLongitude = obj.optString("pubLongitude");
            pubLatitude = obj.optString("pubLatitude");
            startTime = obj.optString("startTime");
            endTime = obj.optString("endTime");
            maxNumber = obj.optInt("maxNumber");
            pubAddress = obj.optString("pubAddress");
            distance = obj.optDouble("distance");
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

        protected MapRedResultModel(Parcel in) {
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
            if (in.readByte() == 0) {
                distance = null;
            } else {
                distance = in.readDouble();
            }
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

        public static final Creator<MapRedResultModel> CREATOR = new Creator<MapRedResultModel>() {
            @Override
            public MapRedResultModel createFromParcel(Parcel in) {
                return new MapRedResultModel(in);
            }

            @Override
            public MapRedResultModel[] newArray(int size) {
                return new MapRedResultModel[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
            if (amount == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(amount);
            }
            dest.writeString(type);
            dest.writeString(pubTime);
            dest.writeString(pubLongitude);
            dest.writeString(pubLatitude);
            dest.writeString(startTime);
            dest.writeString(endTime);
            if (maxNumber == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(maxNumber);
            }
            dest.writeString(pubAddress);
            if (distance == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(distance);
            }
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
