/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: LoginActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-1       新增：Create	
 */

package com.yongf.rorder.app.activity;

import android.support.annotation.NonNull;

import com.yongf.rorder.R;
import com.yongf.rorder.base.BaseActivity;
import com.yongf.rorder.presenter.login.LoginContract;
import com.yongf.rorder.presenter.login.LoginPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * LoginActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V0.1
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    private static final String TAG = "LoginActivity";

    private LoginContract.Presenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();

        LoginPresenter presenter = new LoginPresenter(this);
        setPresenter(presenter);
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
