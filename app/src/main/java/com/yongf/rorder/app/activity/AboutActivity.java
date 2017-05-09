/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: AboutActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-16       新增：Create	
 */

package com.yongf.rorder.app.activity;

import android.widget.ImageView;

import com.yongf.rorder.R;
import com.yongf.rorder.app.application.Config;
import com.yongf.rorder.util.IntentHelper;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * AboutActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-16
 * @see
 * @since ROder V0.1
 */
public class AboutActivity extends BaseActivity {

    private static final String TAG = "AboutActivity";

    @BindView(R.id.tl_title)
    TitleLayout mTitleLayout;
    @BindView(R.id.iv_app_logo)
    ImageView mIvAppLogo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mTitleLayout.setOnLeftIconClickListener(() -> {
            finish();
        });
    }

    @OnClick(R.id.iv_app_logo)
    void onLogoClick() {
        if (Config.DEBUG) {
            Object tag = mIvAppLogo.getTag();
            if (tag == null) {
                mIvAppLogo.setTag("o");
            } else if (tag instanceof String) {
                int length = ((String) tag).length();
                if (length < 2) {
                    mIvAppLogo.setTag(tag + "o");
                } else if (length == 2) {
                    IntentHelper.simpleJump(this, AppEnvSettingActivity.class);
                    mIvAppLogo.setTag(null);
                }
            }
        }
    }
}
