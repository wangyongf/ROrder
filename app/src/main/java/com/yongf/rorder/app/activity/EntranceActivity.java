/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: EntranceActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-16       新增：Create	
 */

package com.yongf.rorder.app.activity;

import com.yongf.rorder.R;
import com.yongf.rorder.util.IntentHelper;

import butterknife.OnClick;

/**
 * EntranceActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-16
 * @see
 * @since ROder V0.1
 */
public class EntranceActivity extends BaseActivity {

    private static final String TAG = "EntranceActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_entrance;
    }

    @OnClick(R.id.about_activity)
    public void go2About() {
        IntentHelper.simpleJump(this, AboutActivity.class);
    }

    @OnClick(R.id.entrance_activity)
    public void go2Entrance() {
        IntentHelper.simpleJump(this, EntranceActivity.class);
    }

    @OnClick(R.id.feedback_activity)
    public void go2Feedback() {
        IntentHelper.simpleJump(this, FeedbackActivity.class);
    }

    @OnClick(R.id.find_pwd_activity)
    public void go2FindPwd() {
        IntentHelper.simpleJump(this, FindPwdActivity.class);
    }

    @OnClick(R.id.login_activity)
    public void go2Login() {
        IntentHelper.simpleJump(this, LoginActivity.class);
    }

    @OnClick(R.id.main_activity)
    public void go2Main() {
        IntentHelper.simpleJump(this, MainActivity.class);
    }

    @OnClick(R.id.register_activity)
    public void go2Register() {
        IntentHelper.simpleJump(this, RegisterActivity.class);
    }

    @OnClick(R.id.account_activity)
    public void go2Account() {
        IntentHelper.simpleJump(this, AccountActivity.class);
    }

    @OnClick(R.id.splash_activity)
    public void go2Splash() {
        IntentHelper.simpleJump(this, SplashActivity.class);
    }

    @OnClick(R.id.setting_activity)
    public void go2Setting() {
        IntentHelper.simpleJump(this, SettingActivity.class);
    }

    @OnClick(R.id.order_activity)
    public void go2Order() {
        IntentHelper.simpleJump(this, OrderActivity.class);
    }

    @OnClick(R.id.slide_layout_activity)
    public void go2SlideLayout() {
        IntentHelper.simpleJump(this, SlideLayoutActivity.class);
    }

    @OnClick(R.id.search_activity)
    public void go2Search() {
        IntentHelper.simpleJump(this, SearchActivity.class);
    }

    @OnClick(R.id.order_activity2)
    void go2Order2() {
        IntentHelper.simpleJump(this, OrderActivity2.class);
    }
}
