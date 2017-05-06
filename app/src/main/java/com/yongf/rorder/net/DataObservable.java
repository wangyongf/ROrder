/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: DataObservable						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-15       新增：Create	
 */

package com.yongf.rorder.net;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.model.BaseBean;
import com.yongf.rorder.model.login.LoginResultBean;
import com.yongf.rorder.model.restaurant.CookbookBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * DataObservable
 *
 * @author Scott Wang
 * @version 1.0, 17-1-15
 * @see
 * @since ROder V0.1
 */
public final class DataObservable {

    // TODO: 17-5-2 替换enum的DataSource
    public static final int TYPE_NETWORK = 0;
    public static final int TYPE_CACHE_DISK = 1;
    public static final int TYPE_CACHE_MEMORY = 2;

    private DataObservable() {
    }

    /**
     * 登录
     *
     * @param mode
     * @param headers
     *
     * @return
     */
    public static Observable<LoginResultBean> login(int mode, Map<String, String> headers) {
        return buildObservable(mode, UrlCenter.LOGIN_URL, RxVolley.Method.POST, headers,
                null, LoginResultBean.class);
    }

    /**
     * 获取商品菜单
     *
     * @param mode
     * @param restaurantId
     *
     * @return
     */
    public static Observable<CookbookBean> goodsData(int mode, int restaurantId) {
        return buildObservable(mode, UrlCenter.COOK_BOOK_PREFIX + restaurantId +
                UrlCenter.COOK_BOOK_SUFFIX, RxVolley.Method.GET, null, null, CookbookBean.class);
    }

    /**
     * build Observable
     *
     * @return
     */
    private static <T extends BaseBean> Observable<T> buildObservable(int mode, String urlSuffix, int method,
                                                                      @Nullable Map<String, String> headers, @Nullable String jsonBody, Class<T> classOfT) {
        // TODO: 17-1-15 完成网络层，网络请求队列
        // TODO: 17-1-16 考虑加入Rx三级缓存
        switch (mode) {
            case TYPE_CACHE_MEMORY:
                // TODO: 17-1-16 return getObservableFromMemory
                break;
            case TYPE_CACHE_DISK:
                // TODO: 17-1-16 return getObservableFromDisk
                // TODO: 17-1-16 updateMemory
                break;
            case TYPE_NETWORK:
                // TODO: 17-1-16 return getObservableFromNetwork
                // TODO: 17-1-16 updateDisk
                // TODO: 17-1-16 updateMemory
                return buildObservableFromNetwork(urlSuffix, method, headers, jsonBody, classOfT);
            default:
                throw new IllegalArgumentException("illegal argument type!");
        }
        return Observable.empty();
    }

    private static <T extends BaseBean> Observable<T> buildObservableFromNetwork(String urlSuffix, int method,
                                                                                 Map<String, String> headers, String jsonBody, Class<T> classOfT) {
        HttpParams params = new HttpParams();
        //headers
        Map<String, String> header = createHeader(headers);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            params.putHeaders(entry.getKey(), entry.getValue());
        }
        //body
        if (!TextUtils.isEmpty(jsonBody)) {
            params.putJsonParams(jsonBody);
        }

        return new RxVolley.Builder()
                .httpMethod(method)
                .url(buildUrl(urlSuffix))
                .params(params)
                .contentType(RxVolley.ContentType.JSON)
                .getResult()
                .map(result -> {
                    String json = new String(result.data);
                    // TODO: 17-5-6 后续还可以在这里拦截code等信息

                    String data = parseData(json);
                    if (data == null) {
                        // TODO: 17-5-6 暂时不会手动触发onError...
                    }

                    return new Gson().fromJson(data, classOfT);
                });
    }

    /**
     * 从返回的统一格式的json数据中解析出data部分(字符串)
     * 目前采取的方式是解析两次(应该能优化?)
     *
     * @param rawJson
     *
     * @return
     */
    private static String parseData(String rawJson) {
        try {
            JSONObject object = new JSONObject(rawJson);
            return object.getJSONObject("data").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建请求头
     *
     * @param map
     *
     * @return
     */
    private static Map<String, String> createHeader(Map<String, String> map) {
        Map<String, String> headers = new HashMap<>();
        if (map != null) {
            headers.putAll(map);
        }
        headers.putAll(createBaseHeader());

        return headers;
    }

    /**
     * 构造请求的完整url
     *
     * @param urlSuffix 请求链接path
     *
     * @return
     */
    private static String buildUrl(String urlSuffix) {
        return AppEnv.getMainSite() + urlSuffix;
    }

    /**
     * 创建网络请求的基本请求头
     *
     * @return
     */
    private static Map<String, String> createBaseHeader() {
        Map<String, String> map = new HashMap<>();
        // FIXME: 17-5-2 DEVICEID生成器
        map.put("X-Deviceid", "1234567890");

        return map;
    }
}
