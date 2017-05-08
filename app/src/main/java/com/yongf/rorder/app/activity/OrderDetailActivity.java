package com.yongf.rorder.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.model.order.OrderDetailResultBean;
import com.yongf.rorder.net.DataObservable;
import com.yongf.rorder.widget.TitleLayout;

import java.util.Random;

import butterknife.BindView;
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
    @BindView(R.id.tv_count2)
    TextView mTvCount2;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_created_at)
    TextView mTvCreatedAt;
    @BindView(R.id.tv_updated_at)
    TextView mTvUpdatedAt;

    private Random mRandom;

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
     * @param status
     */
    private void updateStatus(int status) {
        String text = "";
        switch (status) {
            case 0:
                text = "正在排队,尚未进行制作";
                break;
            default:
                text = "未知状态,请刷新本页面";
                break;
        }
        mTvStatus.setText(text);
    }
}
