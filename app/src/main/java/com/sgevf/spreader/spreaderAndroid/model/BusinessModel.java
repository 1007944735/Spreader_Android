package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class BusinessModel implements Parcelable {
    public Integer id;
    public String bName;
    public String bTime;
    public String bLicense;
    public String bLogo;
    public String bIdcardFront;
    public String bIdcardBack;
    public String bAddress;
    public String bSocialCredit;
    public String bPhone;
    public String bContent;
    public String status;

    public BusinessModel(JSONObject obj) {
        id = obj.optInt("id");
        bName = obj.optString("bName");
        bTime = obj.optString("bTime");
        bLicense = obj.optString("bLicense");
        bLogo = obj.optString("bLogo");
        bIdcardFront = obj.optString("bIdcardFront");
        bIdcardBack = obj.optString("bIdcardBack");
        bAddress = obj.optString("bAddress");
        bSocialCredit = obj.optString("bSocialCredit");
        bPhone = obj.optString("bPhone");
        bContent = obj.optString("bContent");
        status = obj.optString("status");
    }

    protected BusinessModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        bName = in.readString();
        bTime = in.readString();
        bLicense = in.readString();
        bLogo = in.readString();
        bIdcardFront = in.readString();
        bIdcardBack = in.readString();
        bAddress = in.readString();
        bSocialCredit = in.readString();
        bPhone = in.readString();
        bContent = in.readString();
        status = in.readString();
    }

    public static final Creator<BusinessModel> CREATOR = new Creator<BusinessModel>() {
        @Override
        public BusinessModel createFromParcel(Parcel in) {
            return new BusinessModel(in);
        }

        @Override
        public BusinessModel[] newArray(int size) {
            return new BusinessModel[size];
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
        parcel.writeString(bName);
        parcel.writeString(bTime);
        parcel.writeString(bLicense);
        parcel.writeString(bLogo);
        parcel.writeString(bIdcardFront);
        parcel.writeString(bIdcardBack);
        parcel.writeString(bAddress);
        parcel.writeString(bSocialCredit);
        parcel.writeString(bPhone);
        parcel.writeString(bContent);
        parcel.writeString(status);
    }
}
