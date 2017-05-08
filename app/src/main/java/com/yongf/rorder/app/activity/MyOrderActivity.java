package com.yongf.rorder.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.app.application.UserProfile;
import com.yongf.rorder.model.order.OrderDetailResultBean;
import com.yongf.rorder.net.DataObservable;
import com.yongf.rorder.util.IntentHelper;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的订单页面
 *
 * @author Scott Wang
 * @version 1.0, 17-5-7
 * @see
 * @since ROder V1.0
 */
public class MyOrderActivity extends BaseActivity {

    public static final String ORDER_ID = "order_id";
    private static final String TAG = MyOrderActivity.class.getSimpleName();

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.rv_details)
    RecyclerView mRvDetails;

    private MyOrderAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        mRvDetails.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyOrderAdapter(this, null);
        mRvDetails.setAdapter(mAdapter);
        mRvDetails.addItemDecoration(new ShoppingCartActivity.DividerDecoration(this));
    }

    @Override
    protected void initData() {
        int orderId = UserProfile.getInstance().getOrderId();
        if (orderId == -1) {
            AppEnv.getUserToast().simpleToast("没有OrderId, 使用默认的1");
            orderId = 1;
        }

        //根据订单号获取订单详情
        loadOrderDetail(orderId);
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
     * 根据订单号获取订单详情
     *
     * @param orderId
     */
    private void loadOrderDetail(int orderId) {
        getSubscription().add(
                DataObservable.orderDetail(DataObservable.TYPE_NETWORK, orderId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<OrderDetailResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                //获取订单详情数据失败
                                AppEnv.getUserToast().simpleToast("获取订单详情数据失败,请稍后再试");
                            }

                            @Override
                            public void onNext(OrderDetailResultBean orderDetailResultBean) {
                                mAdapter.update(orderDetailResultBean);
                            }
                        })
        );
    }

    public static class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

        private Context mContext;
        private OrderDetailResultBean mOrderDetailResultBean;

        public MyOrderAdapter(Context context, @Nullable OrderDetailResultBean orderDetailResultBean) {
            mContext = context;
            mOrderDetailResultBean = orderDetailResultBean;
        }

        public void update(OrderDetailResultBean bean) {
            mOrderDetailResultBean = bean;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_my_order, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            OrderDetailResultBean.DetailsBean detailsBean = mOrderDetailResultBean.getDetails().get(position);
            holder.bindData(detailsBean);
        }

        @Override
        public int getItemCount() {
            if (mOrderDetailResultBean == null ||
                    mOrderDetailResultBean.getDetails() == null) {
                return 0;
            }
            return mOrderDetailResultBean.getDetails().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.iv_cover)
            ImageView mIvCover;
            @BindView(R.id.tv_name)
            TextView mTvName;
            @BindView(R.id.tv_count)
            TextView mTvCount;
            @BindView(R.id.tv_real_price)
            TextView mTvRealPrice;
            @BindView(R.id.btn_detail)
            Button mBtnDetail;

            private OrderDetailResultBean.DetailsBean mDetailsBean;

            public ViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }

            /**
             * 绑定数据
             *
             * @param detailsBean
             */
            public void bindData(OrderDetailResultBean.DetailsBean detailsBean) {
                mDetailsBean = detailsBean;

                // TODO: 17-5-7 考虑cover的事情...
                if (!detailsBean.getCover().equals("http://www.baidu.com")) {
                    //加载图片...mIvCover
                }
                mTvName.setText(detailsBean.getName());
                mTvCount.setText("份数: " + detailsBean.getQuantity() + "");
                mTvRealPrice.setText("单价: " + detailsBean.getReal_price() + "");
            }

            @OnClick(R.id.btn_detail)
            void go2OrderDetail() {
                int orderDetailId = mDetailsBean.getDetails_id();
                Bundle bundle = new Bundle();
                bundle.putInt(OrderDetailActivity.ORDER_DETAIL_ID, orderDetailId);
                IntentHelper.simpleJump(mContext, OrderDetailActivity.class, bundle);
            }
        }
    }
}
