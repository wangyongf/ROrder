package com.yongf.rorder.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CheckUtils
 *
 * @author Scott Wang
 * @version 1.0, 17-5-3
 * @see
 * @since ROder V1.0
 */
public class CheckUtils {

    /**
     * 判断手机号是否合法
     *
     * @param number
     *
     * @return
     */
    public static boolean isPhone(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }

        Pattern p = Pattern.compile("\\d{11}");
        return p.matcher(number).matches();
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     *
     * @return
     */
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }

        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");                                                           //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");              //复杂匹配
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
