/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BaseView						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     17-1-1       新增：Create	
 */

package com.yongf.rorder.presenter;

/**
 * BaseView
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROrder V0.1
 */
public interface BaseView<T> {

    void setPresenter(T presenter);
}
