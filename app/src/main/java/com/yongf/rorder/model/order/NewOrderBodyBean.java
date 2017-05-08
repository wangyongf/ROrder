package com.yongf.rorder.model.order;

import com.yongf.rorder.model.BaseBean;

import java.util.List;

/**
 * 新建订单请求体
 *
 * @author Scott Wang
 * @version 1.0, 17-5-7
 * @see
 * @since ROder V1.0
 */
public class NewOrderBodyBean extends BaseBean {

    /**
     * notes : 少油少辣
     * restaurant_info_id : 1
     * status : 0
     * tables_id : 1
     * user_info_uid : 123
     * details : [{"goods_id":123,"status":0,"quantity":2},{"goods_id":456,"status":0,"quantity":1},{"goods_id":789,"status":0,"quantity":1}]
     */

    private String notes;
    private int restaurant_info_id;
    private int status;
    private int tables_id;
    private int user_info_uid;
    private List<DetailsBean> details;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getRestaurant_info_id() {
        return restaurant_info_id;
    }

    public void setRestaurant_info_id(int restaurant_info_id) {
        this.restaurant_info_id = restaurant_info_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTables_id() {
        return tables_id;
    }

    public void setTables_id(int tables_id) {
        this.tables_id = tables_id;
    }

    public int getUser_info_uid() {
        return user_info_uid;
    }

    public void setUser_info_uid(int user_info_uid) {
        this.user_info_uid = user_info_uid;
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
         * status : 0
         * quantity : 2
         */

        private int goods_id;
        private int status;
        private int quantity;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
