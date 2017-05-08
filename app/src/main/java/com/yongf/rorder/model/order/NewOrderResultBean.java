package com.yongf.rorder.model.order;

import com.yongf.rorder.model.BaseBean;

/**
 * 新建订单返回值
 *
 * @author Scott Wang
 * @version 1.0, 17-5-7
 * @see
 * @since ROder V1.0
 */
public class NewOrderResultBean extends BaseBean {

    /**
     * order_id : 123
     * waiters_id : 12345
     */

    private int order_id;
    private int waiters_id;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getWaiters_id() {
        return waiters_id;
    }

    public void setWaiters_id(int waiters_id) {
        this.waiters_id = waiters_id;
    }
}
