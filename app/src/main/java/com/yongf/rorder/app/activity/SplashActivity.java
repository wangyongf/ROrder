/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: SplashActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-7       新增：Create	
 */

package com.yongf.rorder.app.activity;

import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.presenter.splash.SplashContract;
import com.yongf.rorder.presenter.splash.SplashPresenter;
import com.yongf.rorder.util.IntentHelper;
import com.yongf.rorder.util.PackageUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 闪屏页
 *
 * @author Scott Wang
 * @version 1.0, 17-1-7
 * @see
 * @since ROder V0.1
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    private static final String TAG = "SplashActivity";

    @BindView(R.id.iv_splash_ad)
    ImageView mIvSplashAd;
    @BindView(R.id.tv_splash_app_version)
    TextView mTvSplashAppVersion;
    @BindView(R.id.btn_splash_skip_ad)
    Button mBtnSplashSkipAd;

    private SplashContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();

        SplashPresenter presenter = new SplashPresenter(this);
        setPresenter(presenter);
    }

    @Override
    protected void initView() {
        super.initView();

        setVersionName();
    }

    /**
     * 设置启动页面的版本号字段
     */
    public void setVersionName() {
        String versionName = PackageUtils.getVersionName(this);
        mTvSplashAppVersion.setText(getString(R.string.app_version_name) + versionName);
    }

    @Override
    public void setPresenter(@NonNull SplashContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.unsubscribe();
    }

    @Override
    @OnClick(R.id.iv_splash_ad)
    public void go2Ad() {

    }

    @Override
    @OnClick(R.id.btn_splash_skip_ad)
    public void go2Home() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        IntentHelper.simpleJump(this, PhoneLoginActivity.class);
        finish();
    }
}
