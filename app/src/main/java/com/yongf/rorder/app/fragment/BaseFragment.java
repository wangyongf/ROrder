/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BaseFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-3-1       新增：Create	
 */

package com.yongf.rorder.app.fragment;

import android.app.Fragment;
import android.widget.Toast;

import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.component.toast.UserToast;

/**
 * BaseFragment
 *
 * @author Scott Wang
 * @version 1.0, 17-3-1
 * @see
 * @since ROder V0.1
 */
public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected static int LENGTH_LONG = Toast.LENGTH_LONG;
    protected static int LENGTH_SHORT = Toast.LENGTH_SHORT;

    /**
     * 获取用户吐司
     *
     * @return
     */
    protected UserToast getUserToast() {
        return AppEnv.getUserToast();
    }
}
