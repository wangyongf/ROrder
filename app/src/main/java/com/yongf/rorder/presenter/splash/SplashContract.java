/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: SplashContract						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-7       新增：Create	
 */

package com.yongf.rorder.presenter.splash;

import com.yongf.rorder.base.BasePresenter;
import com.yongf.rorder.base.BaseView;

/**
 * SplashContract
 *
 * @author Scott Wang
 * @version 1.0, 17-1-7
 * @see
 * @since ROder V0.1
 */
public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void go2Ad();                   //跳转到广告处（一般是一个WebView）

        void go2Home();                 //直接进入主页

        void setVersion();                      //设置Splash界面的版本号字段
    }

    interface Presenter extends BasePresenter {
        void go2HomeWithInterval(int interval);             //经过一定的时间间隔后进入主页
    }
}
