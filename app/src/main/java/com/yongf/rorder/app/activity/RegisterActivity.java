/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: RegisterActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-15       新增：Create	
 */

package com.yongf.rorder.app.activity;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.yongf.rorder.R;
import com.yongf.rorder.util.IntentHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册页面
 *
 * @author Scott Wang
 * @version 1.0, 17-1-15
 * @see
 * @since ROder V0.1
 */
public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.et_username)
    MaterialEditText mEtUsername;

    @BindView(R.id.et_email)
    MaterialEditText mEtEmail;

    @BindView(R.id.et_password)
    MaterialEditText mEtPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.tv_back_login)
    public void onLogin() {
        IntentHelper.simpleJump(this, LoginActivity.class);
    }
}
