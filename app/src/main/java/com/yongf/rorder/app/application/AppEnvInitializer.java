package com.yongf.rorder.app.application;

import android.content.Context;

import com.yongf.rorder.net.UrlCenter;
import com.yongf.rorder.widget.toast.UserToast;

/**
 * 初始化AppEnv
 *
 * @author Scott Wang
 * @version 1.0, 17-5-7
 * @see
 * @since ROder V1.0
 */
public final class AppEnvInitializer {

    public static void init(Context context) {
        AppEnv.setUserToast(new UserToast(context));
        AppEnv.setMainSite(UrlCenter.LOCAL_SITE);

        //初始化xml配置
        Config.init(context);
    }
}
