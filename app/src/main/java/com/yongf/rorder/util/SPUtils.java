package com.yongf.rorder.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yongf.rorder.app.application.Constants;

/**
 * SharedPreferences工具类
 *
 * @author Scott Wang
 * @version 1.0, 17-5-3
 * @see
 * @since ROder V1.0
 */
public class SPUtils {

    /**
     * 设置布尔常量
     *
     * @param context 上下文
     * @param key     关键字
     * @param value   对应的值
     */
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();     //提交保存设置
    }

    /**
     * 获取布尔常量
     *
     * @param context  上下文
     * @param key      关键字
     * @param defValue 设置的默认值
     *
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 设置字符串常量
     *
     * @param context 上下文
     * @param key     关键字
     * @param value   对应的值
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();     //提交保存设置
    }

    /**
     * 获取字符串常量
     *
     * @param context  上下文
     * @param key      关键字
     * @param defValue 设置的默认值
     *
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
}
