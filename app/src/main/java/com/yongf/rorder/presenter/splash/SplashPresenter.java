/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: SplashPresenter						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-7       新增：Create	
 */

package com.yongf.rorder.presenter.splash;

import com.yongf.rorder.util.UIUtil;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * SplashPresenter
 *
 * @author Scott Wang
 * @version 1.0, 17-1-7
 * @see
 * @since ROder V0.1
 */
public class SplashPresenter implements SplashContract.Presenter {

    /**
     * 广告时间, in milliseconds
     */
    public static final int SHOW_AD_TIME = 4000;

    private static final String TAG = "SplashPresenter";

    private final SplashContract.View mView;

    private CompositeSubscription mSubscription;

    public SplashPresenter(SplashContract.View view) {
        mView = checkNotNull(view);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        go2HomeWithInterval(SHOW_AD_TIME);
    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }

    @Override
    public void go2HomeWithInterval(int interval) {
        UIUtil.getMainHandler().postDelayed(() -> mView.go2Home(), interval);
    }
}
