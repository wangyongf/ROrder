/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: MainContract						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-7       新增：Create	
 */

package com.yongf.rorder.presenter.main;

import com.yongf.rorder.presenter.BasePresenter;
import com.yongf.rorder.presenter.BaseView;

/**
 * MainContract
 *
 * @author Scott Wang
 * @version 1.0, 17-1-7
 * @see
 * @since ROder V0.1
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

    }
}
