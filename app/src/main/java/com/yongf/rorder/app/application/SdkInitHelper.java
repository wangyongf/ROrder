package com.yongf.rorder.app.application;


import android.content.Context;

import cn.smssdk.SMSSDK;

/**
 * 专门用来初始化APP中接入的各种SDK
 *
 * @author Scott Wang
 * @version 1.0, 17-4-24
 * @see
 * @since ROder V1.0
 */
public class SdkInitHelper {

    private SdkInitHelper() {

    }

    public static void init(Context context) {
        initSMSSDK(context);
    }

    /**
     * 初始化Mob的短信服务
     * <a href="http://wiki.mob.com/android-%e7%9f%ad%e4%bf%a1sdk%e9%9b%86%e6%88%90%e6%96%87%e6%a1%a3/">android-短信sdk集成文档</a>
     *
     * @param context
     */
    private static void initSMSSDK(Context context) {
        SMSSDK.initSDK(context, Constants.SmsSDK.APP_KEY, Constants.SmsSDK.APP_SECRET);
    }
}
