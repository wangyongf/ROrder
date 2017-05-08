package com.yongf.rorder.net;

/**
 * 存放各种url常量
 *
 * @author Scott Wang
 * @version 1.0, 17-5-2
 * @see
 * @since ROder V1.0
 */
public class UrlCenter {

    //主站链接
    public static final String ALIYUN_SITE = "http://121.42.59.52/ROrder/public";
    public static final String LOCAL_SITE = "http://192.168.2.107/ps/ROrder/public";
    public static final String LOCALHOST_SITE = "http://localhost/ps/ROrder/public";
    public static final String EMULATOR_SITE = "http://10.0.2.2/ps/ROrder/public";

    //登录注册相关
    public static final String LOGIN_URL = "/Login/login.php";

    /////// ------------------- Restaurant ------------------- ///////

    //商品(菜单)
    //api/v1/restaurant/{id}/cookbook
    public static final String COOK_BOOK_PREFIX = "/api/v1/restaurant/";
    public static final String COOK_BOOK_SUFFIX = "/cookbook";

    /////// ------------------- Order ------------------- ///////

    //新建订单接口
    //api/v1/order/order
    public static final String NEW_ORDER = "/api/v1/order/order";
    //根据订单号获取订单详情
    ///api/v1/order/detail/
    public static final String ORDER_DETAIL = "/api/v1/order/detail/";
}
