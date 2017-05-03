/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: AppEnv						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-15       新增：Create	
 */

package com.yongf.rorder.app.application;

import android.content.Context;

import com.yongf.rorder.net.UrlCenter;
import com.yongf.rorder.widget.toast.UserToast;

/**
 * App控制中心
 *
 * @author Scott Wang
 * @version 1.0, 17-1-15
 * @see
 * @since ROder V0.1
 */
public final class AppEnv {

    // TODO: 17-5-3 接入Mob的短信验证码SDK
    // TODO: 17-5-3 完成android端的登录注册功能及界面,并且和后台联调成功!

    private static Context sContext;
    private static String sMainSite = UrlCenter.ALIYUN_SITE;
    private static UserToast sUserToast;

    public static void init(Context context) {
        sContext = context;
        sUserToast = new UserToast(context);

        //初始化xml配置
        Config.init(context);
    }

    public static String getMainSite() {
        return sMainSite;
    }

    public static void setMainSite(String mainSite) {
        sMainSite = mainSite;
    }

    public static UserToast getUserToast() {
        return sUserToast;
    }

    public static void setUserToast(UserToast userToast) {
        sUserToast = userToast;
    }
}
