/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: FindPwdActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-15       新增：Create	
 */

package com.yongf.rorder.app.activity;

import com.yongf.rorder.R;
import com.yongf.rorder.util.IntentHelper;
import com.yongf.rorder.widget.DisplayItemView;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 找回密码页面
 *
 * @author Scott Wang
 * @version 1.0, 17-1-15
 * @see
 * @since ROder V0.1
 */
public class FindPwdActivity extends BaseActivity {

    private static final String TAG = "FindPwdActivity";

    @BindView(R.id.div_input_phone)
    DisplayItemView mDivInputPhone;

    @BindView(R.id.div_input_email)
    DisplayItemView mDivInputEmail;

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mTlTitle.setOnLeftIconClickListener(() -> finish());
    }

    @OnClick(R.id.div_input_phone)
    public void go2InputPhone() {
        IntentHelper.simpleJump(this, InputPhoneActivity.class);
    }

    @OnClick(R.id.div_input_email)
    public void go2InputEmail() {
        IntentHelper.simpleJump(this, InputEmailActivity.class);
    }
}
