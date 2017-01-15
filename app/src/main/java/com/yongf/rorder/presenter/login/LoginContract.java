/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: LoginContract						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-1       新增：Create	
 */

package com.yongf.rorder.presenter.login;

import com.yongf.rorder.presenter.BasePresenter;
import com.yongf.rorder.presenter.BaseView;

/**
 * LoginContract
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V0.1
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showInputError();                  //输入错误提示

        void showNetworkError();            //网络异常提示

        void showLoginError();              //登录失败提示

        void loginSuccess(String uid);          //登录成功，跳转到首页（暂时）
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);           //登录
    }
}
