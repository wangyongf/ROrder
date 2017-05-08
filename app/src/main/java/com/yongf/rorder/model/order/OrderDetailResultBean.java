package com.yongf.rorder.model.order;

import com.yongf.rorder.model.BaseBean;

/**
 * 根据OrderDetailId获取相应的订单详情
 *
 * @author Scott Wang
 * @version 1.0, 17-5-8
 * @see
 * @since ROder V1.0
 */
public class OrderDetailResultBean extends BaseBean {

    /**
     * goods_raw_id : 1234567890
     * goods_id : 1
     * name : 卤蛋
     * original_price : 17.2
     * real_price : 15.1
     * cover : http://www.baidu.com
     * pictures : 此处是一段json字符串
     * status : 0
     * quantity : 2
     * order_raw_id : 1
     * order_id : 1234
     * created_at : 2017-05-08 17:08:27
     * updated_at : 2017-05-08 17:08:27
     */

    private int goods_raw_id;
    private String goods_id;
    private String name;
    private double original_price;
    private double real_price;
    private String cover;
    private String pictures;
    private int status;
    private int quantity;
    private int order_raw_id;
    private String order_id;
    private String created_at;
    private String updated_at;

    public int getGoods_raw_id() {
        return goods_raw_id;
    }

    public void setGoods_raw_id(int goods_raw_id) {
        this.goods_raw_id = goods_raw_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getReal_price() {
        return real_price;
    }

    public void setReal_price(double real_price) {
        this.real_price = real_price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
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

    public int getOrder_raw_id() {
        return order_raw_id;
    }

    public void setOrder_raw_id(int order_raw_id) {
        this.order_raw_id = order_raw_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
