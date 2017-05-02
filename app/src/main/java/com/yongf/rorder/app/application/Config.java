/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: Conf						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-15       新增：Create	
 */

package com.yongf.rorder.app.application;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * app的配置
 *
 * @author Scott Wang
 * @version 1.0, 17-1-15
 * @see
 * @since ROder V0.1
 */
public final class Config {

    public static final String CONFIG = "config.xml";
    public static boolean DEBUG = false;                //app是否处于调试模式

    public static void init(Context context) {
        initConfig(context);
    }

    /**
     * 从config.xml中读取配置
     *
     * @param context
     */
    private static void initConfig(Context context) {
        try {
            InputStream is = context.getAssets().open(CONFIG);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "UTF-8");

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("debug")) {
                            parser.next();              //指向下一个(属性值)
                            DEBUG = Boolean.parseBoolean(parser.getText());
                        }
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
