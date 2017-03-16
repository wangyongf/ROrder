package com.yongf.rorder.component.dialog;
/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: UserDialog						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-3-14       新增：Create	
 */

import android.content.Context;
import android.support.annotation.NonNull;

public class UserDialog {

    public static CustomDialog customDialog(@NonNull Context context, String title, String message, String[] buttons, boolean cancelable,
                                            CustomDialog.OnButtonClickListener onButtonClickListener) {
        CustomDialog dialog = new CustomDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setButton(buttons)
                .setOnButtonClickListener(onButtonClickListener)
                .setCancelable(cancelable)
                .build();

        return dialog;
    }
}
