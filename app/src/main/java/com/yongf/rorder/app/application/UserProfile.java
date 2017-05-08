package com.yongf.rorder.app.application;

/**
 * 用户信息(用户资料)
 *
 * @author Scott Wang
 * @version 1.0, 17-5-7
 * @see
 * @since ROder V1.0
 */
public class UserProfile {

    private static UserProfile sUserProfile;

    private int uid = 1234567890;
    private int orderId = 1;

    private UserProfile() {

    }

    public synchronized static UserProfile getInstance() {
        if (sUserProfile == null) {
            sUserProfile = new UserProfile();
        }
        return sUserProfile;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
