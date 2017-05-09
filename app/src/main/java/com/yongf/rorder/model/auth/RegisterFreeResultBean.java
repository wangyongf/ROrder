package com.yongf.rorder.model.auth;

import com.yongf.rorder.model.BaseBean;

/**
 * 免注册登录返回值
 *
 * @author Scott Wang
 * @version 1.0, 17-5-9
 * @see
 * @since ROder V1.0
 */
public class RegisterFreeResultBean extends BaseBean {

    /**
     * uid : 00001234567890123456
     * mobile : 15221382253
     * nickname : rorder_1234567890123456
     * signature : 哪有什么岁月静好,不过是有人替你负重前行
     * sex : 0
     * birthday : 1996-03-01 00:00:00
     * realname : null
     * email : 1059613472@qq.com
     * user_avatar : http://1234.qiniuyun.com/image.png
     * token : JKLWESJKDS83
     * refresh_token : 34KESJ94J6K1
     */

    private String uid;
    private String mobile;
    private String nickname;
    private String signature;
    private String sex;
    private String birthday;
    private Object realname;
    private String email;
    private String user_avatar;
    private String token;
    private String refresh_token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Object getRealname() {
        return realname;
    }

    public void setRealname(Object realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
