package com.yongf.rorder.model.order;

import com.yongf.rorder.model.BaseBean;

import java.util.List;

/**
 * 顾客端更新订单请求体body
 *
 * @author Scott Wang
 * @version 1.0, 17-5-8
 * @see
 * @since ROder V1.0
 */
public class UpdateOrderDetailBodyBean extends BaseBean {

    /**
     * order_id : 123
     * status : 0
     * details : [{"goods_id":123,"count":1},{"goods_id":456,"count":-1}]
     */

    private int order_id;
    private int status;
    private List<DetailsBean> details;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * goods_id : 123
         * count : 1
         */

        private int goods_id;
        private int count;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
