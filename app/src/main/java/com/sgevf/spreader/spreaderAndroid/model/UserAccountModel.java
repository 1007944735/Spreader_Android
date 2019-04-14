package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class UserAccountModel implements Parcelable {
    public Integer id;
    public String balance;
    public String alipayAccount;

    public UserAccountModel(JSONObject obj){
        id=obj.optInt("id");
        balance=obj.optString("balance");
        alipayAccount=obj.optString("alipay_account");
    }

    protected UserAccountModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        balance = in.readString();
        alipayAccount = in.readString();
    }

    public static final Creator<UserAccountModel> CREATOR = new Creator<UserAccountModel>() {
        @Override
        public UserAccountModel createFromParcel(Parcel in) {
            return new UserAccountModel(in);
        }

        @Override
        public UserAccountModel[] newArray(int size) {
            return new UserAccountModel[size];
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
        dest.writeString(balance);
        dest.writeString(alipayAccount);
    }
}
