package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

public class CardListModel implements Parcelable {

    public List<CardManagerModel> list;

    protected CardListModel(Parcel in) {
        list = in.createTypedArrayList(CardManagerModel.CREATOR);
    }

    public static final Creator<CardListModel> CREATOR = new Creator<CardListModel>() {
        @Override
        public CardListModel createFromParcel(Parcel in) {
            return new CardListModel(in);
        }

        @Override
        public CardListModel[] newArray(int size) {
            return new CardListModel[size];
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

    public static class CardManagerModel implements Parcelable {

        public int id;
        public String discountRule;
        public String useRule;
        public String startTime;
        public String effectiveTime;
        public String status;
        public String sellerName;
        public boolean isSelected = false;

        public CardManagerModel(JSONObject obj) {
            id = obj.optInt("id");
            discountRule = obj.optString("discountRule");
            useRule = obj.optString("useRule");
            startTime = obj.optString("startTime");
            effectiveTime = obj.optString("effectiveTime");
            status = obj.optString("status");
            sellerName = obj.optString("sellerName");
        }

        protected CardManagerModel(Parcel in) {
            id = in.readInt();
            discountRule = in.readString();
            useRule = in.readString();
            startTime = in.readString();
            effectiveTime = in.readString();
            status = in.readString();
            sellerName = in.readString();
            isSelected = in.readByte() != 0;
        }

        public static final Creator<CardManagerModel> CREATOR = new Creator<CardManagerModel>() {
            @Override
            public CardManagerModel createFromParcel(Parcel in) {
                return new CardManagerModel(in);
            }

            @Override
            public CardManagerModel[] newArray(int size) {
                return new CardManagerModel[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(discountRule);
            dest.writeString(useRule);
            dest.writeString(startTime);
            dest.writeString(effectiveTime);
            dest.writeString(status);
            dest.writeString(sellerName);
            dest.writeByte((byte) (isSelected ? 1 : 0));
        }
    }
}
