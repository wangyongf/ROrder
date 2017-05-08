package com.yongf.rorder.model.order;

import com.yongf.rorder.model.BaseBean;

import java.util.List;

/**
 * 顾客端更新订单详情结果
 *
 * @author Scott Wang
 * @version 1.0, 17-5-8
 * @see
 * @since ROder V1.0
 */
public class UpdateOrderDetailResultBean extends BaseBean {

    private List<UpdateResultBean> update_result;

    public List<UpdateResultBean> getUpdate_result() {
        return update_result;
    }

    public void setUpdate_result(List<UpdateResultBean> update_result) {
        this.update_result = update_result;
    }

    public static class UpdateResultBean {
        /**
         * goods_id : 123
         * goods_name : 卤蛋
         * result : 0
         */

        private int goods_id;
        private String goods_name;
        private int result;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }
}
