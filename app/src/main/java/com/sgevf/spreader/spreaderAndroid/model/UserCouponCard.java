package com.sgevf.spreader.spreaderAndroid.model;

import java.util.List;

public class UserCouponCard {
    public int sellerId;
    public String sellerName;
    public String sellerLogo;
    public List<Coupon> cards;

    public class Coupon {
        public int id;
        public String getTime;
        public String endTime;
        public String status;
        public String discountRule;
        public String useRule;
        public int redPacketId;
    }

}
