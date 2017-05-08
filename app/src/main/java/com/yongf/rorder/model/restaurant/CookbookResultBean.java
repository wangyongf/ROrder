package com.yongf.rorder.model.restaurant;

import com.google.gson.annotations.SerializedName;
import com.yongf.rorder.model.BaseBean;

import java.util.List;

/**
 * 菜谱Cookbook
 */
public class CookbookResultBean extends BaseBean {

    private List<CategoriesBean> categories;

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class CategoriesBean {
        /**
         * category_info : {"id":123456,"category_id":"123456","name":"美味湘菜"}
         * childs : [{"id":123456789,"goods_id":"abd123456","name":"卤蛋","original_price":17.2,"real_price":15.1,"cover":"http://www.baidu.com","description":"肥牛美味而且营养丰富, 蛋白质, 铁, \u203b,维生素等等.","pictures":"此处是一段json字符串"},{"id":123456,"goods_id":"123456","name":"孜然肥牛饭","original_price":20.1,"real_price":48.1,"cover":"http://blog.54yongf.com","description":"卤蛋","pictures":"此处是一段json字符串"}]
         */

        private CategoryInfoBean category_info;
        private List<ChildsBean> childs;

        public CategoryInfoBean getCategory_info() {
            return category_info;
        }

        public void setCategory_info(CategoryInfoBean category_info) {
            this.category_info = category_info;
        }

        public List<ChildsBean> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBean> childs) {
            this.childs = childs;
        }

        public static class CategoryInfoBean {
            /**
             * id : 123456
             * category_id : 123456
             * name : 美味湘菜
             */

            @SerializedName("id")
            private int idX;
            private String category_id;
            private String name;

            public int getIdX() {
                return idX;
            }

            public void setIdX(int idX) {
                this.idX = idX;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ChildsBean {
            /**
             * id : 123456789
             * goods_id : abd123456
             * name : 卤蛋
             * original_price : 17.2
             * real_price : 15.1
             * cover : http://www.baidu.com
             * description : 肥牛美味而且营养丰富, 蛋白质, 铁, ※,维生素等等.
             * pictures : 此处是一段json字符串
             */

            @SerializedName("id")
            private int idX;
            private String goods_id;
            private String name;
            private double original_price;
            private double real_price;
            private String cover;
            private String description;
            private String pictures;

            public int getIdX() {
                return idX;
            }

            public void setIdX(int idX) {
                this.idX = idX;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPictures() {
                return pictures;
            }

            public void setPictures(String pictures) {
                this.pictures = pictures;
            }
        }
    }
}
