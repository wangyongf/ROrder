package com.yongf.rorder.app.activity;

import com.google.gson.GsonBuilder;
import com.yongf.rorder.BuildConfig;
import com.yongf.rorder.MyRobolectricTestRunner;
import com.yongf.rorder.app.activity.ShoppingCartActivity.GoodsItem;
import com.yongf.rorder.app.application.UserProfile;
import com.yongf.rorder.model.order.NewOrderBodyBean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(MyRobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ShoppingCartActivityTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void buildNewOrderJsonBody() {
        List<GoodsItem> selectedList = initData();

        NewOrderBodyBean newOrderBodyBean = new NewOrderBodyBean();
        newOrderBodyBean.setNotes("少油微辣");
        newOrderBodyBean.setRestaurant_info_id(com.yongf.rorder.app.application.Config.RESTAURANT_ID);
        newOrderBodyBean.setStatus(0);
        newOrderBodyBean.setTables_id(1);
        newOrderBodyBean.setUser_info_uid(UserProfile.getInstance().getUid());

        NewOrderBodyBean.DetailsBean detailsBean;
        List<NewOrderBodyBean.DetailsBean> detailsBeanList = new ArrayList<>();
        for (int i = 0; i < selectedList.size(); i++) {
            GoodsItem item = selectedList.get(i);
            detailsBean = new NewOrderBodyBean.DetailsBean();
            detailsBean.setGoods_id(item.id);
            detailsBean.setStatus(0);
            detailsBean.setQuantity(item.count);
            detailsBeanList.add(detailsBean);
        }

        newOrderBodyBean.setDetails(detailsBeanList);

        System.out.println(new GsonBuilder().setPrettyPrinting()
                .create().toJson(newOrderBodyBean));
    }

    private List<GoodsItem> initData() {
        List<GoodsItem> goodsList = new ArrayList<>();
        GoodsItem item;
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 2; j++) {
                item = new GoodsItem(100 * i + j, Math.random() * 100, "商品" + (100 * i + j), i, "种类" + i);
                goodsList.add(item);
            }
        }

        return goodsList;
    }
}
