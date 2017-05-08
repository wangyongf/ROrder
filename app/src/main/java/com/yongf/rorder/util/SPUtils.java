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
        initSP(context).edit().putBoolean(key, value).commit();     //提交保存设置
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
        return initSP(context).getBoolean(key, defValue);
    }

    /**
     * 设置字符串常量
     *
     * @param context 上下文
     * @param key     关键字
     * @param value   对应的值
     */
    public static void setString(Context context, String key, String value) {
        initSP(context).edit().putString(key, value).commit();     //提交保存设置
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
        return initSP(context).getString(key, defValue);
    }

    /**
     * 设置整数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setInt(Context context, String key, int value) {
        initSP(context).edit().putInt(key, value);
    }

    /**
     * 获取整数
     *
     * @param context
     * @param key
     * @param defValue
     *
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        return initSP(context).getInt(key, defValue);
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context
     *
     * @return
     */
    private static SharedPreferences initSP(Context context) {
        return context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE);
    }
}
