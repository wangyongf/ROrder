/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: DataObservable						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-15       新增：Create	
 */

package com.yongf.rorder.app.application;

import com.yongf.rorder.model.BaseBean;
import com.yongf.rorder.model.login.LoginBean;

import rx.Observable;

/**
 * DataObservable
 *
 * @author Scott Wang
 * @version 1.0, 17-1-15
 * @see
 * @since ROder V0.1
 */
public final class DataObservable {

    public static final int TYPE_NETWORK = 0;
    public static final int TYPE_CACHE_DISK = 1;
    public static final int TYPE_CACHE_MEMORY = 2;

    private DataObservable() {
    }

    public static Observable<? extends BaseBean> login(String urlSuffix, String username, String password) {
        LoginBean loginBean = new LoginBean(username, password);
        return buildObservable(buildUrl(urlSuffix), TYPE_NETWORK, loginBean);
    }

    /**
     * build Observable
     *
     * @return
     */
    private static Observable<? extends BaseBean> buildObservable(String url, int mode, BaseBean bean) {
        // TODO: 17-1-15 完成网络层，网络请求队列
        // TODO: 17-1-16 考虑加入Rx三级缓存
        switch (mode) {
            case TYPE_CACHE_MEMORY:
                // TODO: 17-1-16 return getObservableFromMemory
                break;
            case TYPE_CACHE_DISK:
                // TODO: 17-1-16 return getObservableFromDisk
                // TODO: 17-1-16 updateMemory
                break;
            case TYPE_NETWORK:
                // TODO: 17-1-16 return getObservableFromNetwork
                // TODO: 17-1-16 updateDisk
                // TODO: 17-1-16 updateMemory
                break;
            default:
                throw new IllegalArgumentException("illegal argument type!");
        }
        return Observable.empty();
    }

    /**
     * 构造请求的完整url
     *
     * @param urlSuffix 请求链接path
     *
     * @return
     */
    private static String buildUrl(String urlSuffix) {
        return AppEnv.getMainSite() + urlSuffix;
    }
}
