/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: IntentHelper						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-16       新增：Create	
 */

package com.yongf.rorder.util;

import android.content.Context;
import android.content.Intent;

/**
 * IntentHelper
 *
 * @author Scott Wang
 * @version 1.0, 17-1-16
 * @see
 * @since ROder V0.1
 */
public class IntentHelper {

    /**
     * 简单地从当前页面跳转到指定页面
     *
     * @param context
     * @param clazz
     */
    public static void simpleJump(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
