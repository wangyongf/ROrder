package com.yongf.rorder.app.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yongf.rorder.R;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.model.order.OrderDetailResultBean;
import com.yongf.rorder.model.order.UpdateOrderDetailBodyBean;
import com.yongf.rorder.model.order.UpdateOrderDetailResultBean;
import com.yongf.rorder.net.DataObservable;
import com.yongf.rorder.widget.TitleLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 订单详情页面
 *
 * @author Scott Wang
 * @version 1.0, 17-5-8
 * @see
 * @since ROder V1.0
 */
public class OrderDetailActivity extends BaseActivity {

    public static final String ORDER_DETAIL_ID = "order_detail_id";
    private static final String TAG = OrderDetailActivity.class.getSimpleName();

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_cover)
    ImageView mIvCover;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rb_score)
    RatingBar mRbScore;
    @BindView(R.id.tv_real_price)
    TextView mTvRealPrice;
    @BindView(R.id.tv_minus)
    TextView mTvMinus;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tv_count2)
    TextView mTvCount2;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_created_at)
    TextView mTvCreatedAt;
    @BindView(R.id.tv_updated_at)
    TextView mTvUpdatedAt;

    private Random mRandom;
    private OrderDetailResultBean mOrderDetailResultBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void before() {
        mRandom = new Random();
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        int orderDetailId = -1;
        if (bundle != null) {
            orderDetailId = bundle.getInt(ORDER_DETAIL_ID, -1);
        }
        if (orderDetailId == -1) {
            AppEnv.getUserToast().simpleToast("没有OrderDetailId, 使用默认的1");
            orderDetailId = 1;
        }

        loadOrderDetail(orderDetailId);
    }

    @Override
    protected void initEvent() {
        mTlTitle.setOnLeftIconClickListener(() -> finish());
    }

    @Override
    protected boolean needRefreshDataWhenResume() {
        return true;
    }

    /**
     * 加载这一条订单详情的数据
     *
     * @param orderDetailId
     */
    private void loadOrderDetail(int orderDetailId) {
        getSubscription().add(
                DataObservable.orderDetail(DataObservable.TYPE_NETWORK, orderDetailId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<OrderDetailResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                AppEnv.getUserToast().simpleToast("加载订单详情数据失败,请稍后再试");
                            }

                            @Override
                            public void onNext(OrderDetailResultBean bean) {
                                mOrderDetailResultBean = bean;
                                update(bean);
                            }
                        })
        );
    }

    /**
     * 网络请求数据,更新UI
     *
     * @param bean
     */
    private void update(@NonNull OrderDetailResultBean bean) {
        updateStatus(bean.getStatus());
        updateCover(bean.getCover());

        mTvName.setText(bean.getName());
        mRbScore.setRating(mRandom.nextInt(5));
        mTvRealPrice.setText("¥" + bean.getReal_price() + "");
        mTvCount.setText(bean.getQuantity() + "");
        mTvCount2.setText(bean.getQuantity() + "");
        mTvOrderNumber.setText(bean.getOrder_id());
        mTvCreatedAt.setText(bean.getCreated_at());
        mTvUpdatedAt.setText(bean.getUpdated_at());
    }

    /**
     * 更新菜品的封面图片
     *
     * @param coverUrl
     */
    private void updateCover(String coverUrl) {

    }

    /**
     * 更新状态
     *
     * 进度:
     * 0-尚未开始制作
     * 1-正在制作
     * 2-制作完成上菜中
     * 3-上菜完毕
     *
     * @param status
     */
    private void updateStatus(int status) {
        String text = "";
        switch (status) {
            case 0:
                text = "正在排队,尚未开始制作";
                break;
            case 1:
                text = "正在制作";
                break;
            case 2:
                text = "制作完成上菜中";
                break;
            case 3:
                text = "上菜完毕";
                break;
            default:
                text = "未知状态,请刷新本页面";
                break;
        }
        mTvStatus.setText(text);
    }

    /**
     * 减少份数
     */
    @OnClick(R.id.tv_minus)
    void onMinus() {
        int finalCount = Integer.parseInt(mTvCount.getText().toString().trim());
        if (finalCount > 0) {
            mTvCount.setText((finalCount - 1) + "");
        }
    }

    /**
     * 增加份数
     */
    @OnClick(R.id.tv_add)
    void onAdd() {
        int finalCount = Integer.parseInt(mTvCount.getText().toString().trim());
        mTvCount.setText((finalCount + 1) + "");
    }

    @OnClick(R.id.tv_submit)
    void onSubmit() {
        int finalCount = Integer.parseInt(mTvCount.getText().toString().trim());
        int diff = finalCount - mOrderDetailResultBean.getQuantity();
        if (diff == 0) {
            AppEnv.getUserToast().simpleToast("请先修改下单数量哦");
            return;
        }

        String message = "";
        message += "修改订单-" + mOrderDetailResultBean.getName() + "\n";
        message += "修改前数量: " + mOrderDetailResultBean.getQuantity() + "\n";
        message += "修改后数量: " + finalCount + "\n";
        message += (finalCount > mOrderDetailResultBean.getQuantity()) ? "新增" : "减少";
        message += "了" + Math.abs(diff) + "份\n";
        message += "是否确认?";
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("修改订单确认")
                .setMessage(message)
                .setNegativeButton("取消", (dialog12, which) -> AppEnv.getUserToast().simpleToast("取消修改订单"))
                .setPositiveButton("确定", (dialog1, which) -> {
                    AppEnv.getUserToast().simpleToast("正在提交修改后的订单,请稍后");
                    executeSubmit(diff);
                })
                .create();
        dialog.show();
    }

    /**
     * 真正提交修改后的订单
     *
     * @param diff
     */
    private void executeSubmit(int diff) {
        String jsonBody = buildUpdateOrderDetailJsonBody(diff);
        getSubscription().add(
                DataObservable.updateOrderDetail(DataObservable.TYPE_NETWORK, jsonBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<UpdateOrderDetailResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                AppEnv.getUserToast().simpleToast("订单修改提交失败,请刷新页面后再试");
                                //刷新一下数据
                                initData();
                            }

                            @Override
                            public void onNext(UpdateOrderDetailResultBean updateOrderDetailResultBean) {
                                if (checkUpdateResult(updateOrderDetailResultBean)) {
                                    AppEnv.getUserToast().simpleToast("订单修改成功!");
                                } else {
                                    AppEnv.getUserToast().simpleToast("已经下锅,不能修改订单!", LENGTH_LONG);
                                }
                                initData();
                            }
                        })
        );
    }

    /**
     * 从返回结果中确定修改是否成功
     *
     * @param bean
     *
     * @return
     */
    private boolean checkUpdateResult(UpdateOrderDetailResultBean bean) {
        List<UpdateOrderDetailResultBean.UpdateResultBean> updateResult = bean.getUpdate_result();
        // TODO: 17-5-8 目前返回的数组中只可能有一个元素!
        UpdateOrderDetailResultBean.UpdateResultBean updateResultBean = updateResult.get(0);
        return updateResultBean.getResult() == 0;
    }

    /**
     * 构造顾客端更新订单请求的请求体body
     *
     * @param diff
     *
     * @return
     */
    private String buildUpdateOrderDetailJsonBody(int diff) {
        UpdateOrderDetailBodyBean updateOrderDetailBodyBean = new UpdateOrderDetailBodyBean();
        updateOrderDetailBodyBean.setOrder_id(mOrderDetailResultBean.getOrder_raw_id());
        updateOrderDetailBodyBean.setStatus(mOrderDetailResultBean.getStatus());

        UpdateOrderDetailBodyBean.DetailsBean detailsBean;
        List<UpdateOrderDetailBodyBean.DetailsBean> detailsBeanList = new ArrayList<>();
        detailsBean = new UpdateOrderDetailBodyBean.DetailsBean();
        detailsBean.setGoods_id(mOrderDetailResultBean.getGoods_raw_id());
        detailsBean.setCount(diff);
        detailsBeanList.add(detailsBean);

        updateOrderDetailBodyBean.setDetails(detailsBeanList);

        return new Gson().toJson(updateOrderDetailBodyBean);
    }
}
