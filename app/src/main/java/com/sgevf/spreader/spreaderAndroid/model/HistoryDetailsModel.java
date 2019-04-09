package com.sgevf.spreader.spreaderAndroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryDetailsModel {
    public ArrayList<HistoryListModel> list;

    public static class HistoryListModel implements Parcelable{
        public Integer id;
        public String type;
        public String money;
        public String time;
        public Integer redPacketId;

        public HistoryListModel(JSONObject obj) {
            id = obj.optInt("id");
            type = obj.optString("type");
            money = obj.optString("money");
            time = obj.optString("time");
            redPacketId = obj.optInt("red_packet_id");
        }

        protected HistoryListModel(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            type = in.readString();
            money = in.readString();
            time = in.readString();
            if (in.readByte() == 0) {
                redPacketId = null;
            } else {
                redPacketId = in.readInt();
            }
        }

        public static final Creator<HistoryListModel> CREATOR = new Creator<HistoryListModel>() {
            @Override
            public HistoryListModel createFromParcel(Parcel in) {
                return new HistoryListModel(in);
            }

            @Override
            public HistoryListModel[] newArray(int size) {
                return new HistoryListModel[size];
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
            dest.writeString(type);
            dest.writeString(money);
            dest.writeString(time);
            if (redPacketId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(redPacketId);
            }
        }
    }
}
