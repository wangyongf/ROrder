/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: LoginPresenter						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-1       新增：Create	
 */

package com.yongf.rorder.presenter.login;

import android.text.TextUtils;
import android.widget.Toast;

import com.yongf.rorder.app.application.DataObservable;
import com.yongf.rorder.app.application.MyApplication;
import com.yongf.rorder.app.application.UrlCenter;
import com.yongf.rorder.model.BaseBean;
import com.yongf.rorder.model.login.LoginResultBean;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * LoginPresenter
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V0.1
 */
public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";

    private final LoginContract.View mView;

    private CompositeSubscription mSubscription;

    public LoginPresenter(LoginContract.View view) {
        mView = checkNotNull(view);
        mSubscription = new CompositeSubscription();
    }

    /**
     * 可以考虑做一些网络请求的事情
     */
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }

    @Override
    public void login(String username, String password) {
        if (!checkInput(username, password)) {
            mView.showInputError();
            return;
        }
        performLogin(username, password);
    }

    private boolean checkInput(String username, String password) {
        return !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
    }

    /**
     * 请求网络，执行登录过程
     *
     * @param username 用户名
     * @param password 密码
     */
    private void performLogin(String username, String password) {
        mSubscription.add(
                DataObservable.login(UrlCenter.LOGIN_URL, username, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                                Toast.makeText(MyApplication.getContext(), "onCompleted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.showNetworkError();
                            }

                            @Override
                            public void onNext(BaseBean bean) {
                                LoginResultBean loginResultBean = (LoginResultBean) bean;
                                // TODO: 17-1-15 完成登录逻辑

                                String uid = "001";
                                boolean isLoginSuccess = false;
                                if (isLoginSuccess) {
                                    mView.loginSuccess(uid);
                                } else {
                                    mView.showLoginError();
                                }
                            }
                        })
        );
    }
}
