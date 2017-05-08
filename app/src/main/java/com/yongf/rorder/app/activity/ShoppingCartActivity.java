package com.yongf.rorder.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.yongf.rorder.R;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.app.application.Config;
import com.yongf.rorder.app.application.MyApplication;
import com.yongf.rorder.app.application.UserProfile;
import com.yongf.rorder.model.order.NewOrderBodyBean;
import com.yongf.rorder.model.order.NewOrderResultBean;
import com.yongf.rorder.model.restaurant.CookbookResultBean;
import com.yongf.rorder.net.DataObservable;
import com.yongf.rorder.util.IntentHelper;
import com.yongf.rorder.widget.TitleLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ShoppingCartActivity extends BaseActivity {

    private static final String TAG = ShoppingCartActivity.class.getSimpleName();
    private static double MIN_CONSUMPTION = 10;                //最低消费

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.imgCart)
    ImageView imgCart;
    @BindView(R.id.containerLayout)
    ViewGroup anim_mask_layout;
    @BindView(R.id.typeRecyclerView)
    RecyclerView rvType;
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.tvCost)
    TextView tvCost;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.tvTips)
    TextView tvTips;
    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.itemListView)
    StickyListHeadersListView listView;

    private View bottomSheet;
    private RecyclerView rvSelected;

    private ArrayList<GoodsItem> dataList = new ArrayList<>();
    private ArrayList<GoodsItem> typeList = new ArrayList<>();
    private SparseArray<GoodsItem> selectedList;
    private SparseIntArray groupSelect;
    private GoodsAdapter myAdapter;
    private SelectAdapter selectAdapter;
    private TypeAdapter typeAdapter;
    private NumberFormat nf;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void before() {
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        selectedList = new SparseArray<>();
        groupSelect = new SparseIntArray();
    }

    @Override
    protected void initView() {
        rvType.setLayoutManager(new LinearLayoutManager(this));
        typeAdapter = new TypeAdapter(this, typeList);
        rvType.setAdapter(typeAdapter);
        rvType.addItemDecoration(new DividerDecoration(this));

        myAdapter = new GoodsAdapter(dataList, this);
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void initData() {
        loadCookbook();
    }

    @Override
    protected void initEvent() {
        mTlTitle.setOnLeftIconClickListener(() -> finish());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= dataList.size()) {
                    return;
                }

                GoodsItem item = dataList.get(firstVisibleItem);
                if (typeAdapter.selectTypeId != item.typeId) {
                    typeAdapter.selectTypeId = item.typeId;
                    typeAdapter.notifyDataSetChanged();
                    rvType.smoothScrollToPosition(getSelectedGroupPosition(item.typeId));
                }
            }
        });
    }

    @Override
    protected boolean needRefreshDataWhenResume() {
        return true;
    }

    //根据类别id获取分类的Position 用于滚动左侧的类别列表
    public int getSelectedGroupPosition(int typeId) {
        for (int i = 0; i < typeList.size(); i++) {
            if (typeId == typeList.get(i).typeId) {
                return i;
            }
        }
        return 0;
    }

    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.button_add);
        setAnim(img, start_location);
    }

    private void setAnim(final View v, int[] start_location) {
        addViewToAnimLayout(anim_mask_layout, v, start_location);
        Animation set = createAnim(start_location[0], start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                MyApplication.getHandler().postDelayed(() -> anim_mask_layout.removeView(v), 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    private void addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }

    private Animation createAnim(int startX, int startY) {
        int[] des = new int[2];
        imgCart.getLocationInWindow(des);

        AnimationSet set = new AnimationSet(false);

        Animation translationX = new TranslateAnimation(0, des[0] - startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1] - startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1, 0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);

        return set;
    }

    //添加商品
    public void add(GoodsItem item, boolean refreshGoodList) {

        int groupCount = groupSelect.get(item.typeId);
        if (groupCount == 0) {
            groupSelect.append(item.typeId, 1);
        } else {
            groupSelect.append(item.typeId, ++groupCount);
        }

        GoodsItem temp = selectedList.get(item.id);
        if (temp == null) {
            item.count = 1;
            selectedList.append(item.id, item);
        } else {
            temp.count++;
        }
        update(refreshGoodList);
    }

    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count = 0;
        double cost = 0;
        for (int i = 0; i < size; i++) {
            GoodsItem item = selectedList.valueAt(i);
            count += item.count;
            cost += item.count * item.price;
        }

        if (count < 1) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));

        if (cost > MIN_CONSUMPTION) {
            tvTips.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
        }

        tvCost.setText(nf.format(cost));

        if (myAdapter != null && refreshGoodList) {
            myAdapter.notifyDataSetChanged();
        }
        if (selectAdapter != null) {
            selectAdapter.notifyDataSetChanged();
        }
        if (typeAdapter != null) {
            typeAdapter.notifyDataSetChanged();
        }
        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }

    //移除商品
    public void remove(GoodsItem item, boolean refreshGoodList) {

        int groupCount = groupSelect.get(item.typeId);
        if (groupCount == 1) {
            groupSelect.delete(item.typeId);
        } else if (groupCount > 1) {
            groupSelect.append(item.typeId, --groupCount);
        }

        GoodsItem temp = selectedList.get(item.id);
        if (temp != null) {
            if (temp.count < 2) {
                selectedList.remove(item.id);
            } else {
                item.count--;
            }
        }
        update(refreshGoodList);
    }

    @OnClick(R.id.tvSubmit)
    void onSubmit() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("下单确认")
                .setMessage("确认下单?")
                .setPositiveButton("确认", (dialog1, which) -> newOrder())
                .setNegativeButton("取消", (dialog12, which) -> AppEnv.getUserToast().simpleToast("取消下单"))
                .create();
        dialog.show();
    }

    /**
     * 下单
     */
    private void newOrder() {
        AppEnv.getUserToast().simpleToast("正在下单");

        String jsonBody = buildNewOrderJsonBody();
        getSubscription().add(
                DataObservable.newOrder(DataObservable.TYPE_NETWORK, jsonBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<NewOrderResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                AppEnv.getUserToast().simpleToast("下单失败,请稍后再试");
                            }

                            @Override
                            public void onNext(NewOrderResultBean newOrderResultBean) {
                                AppEnv.getUserToast().simpleToast("下单成功,正在跳转");

                                int orderId = newOrderResultBean.getOrder_id();
                                UserProfile.getInstance().setOrderId(orderId);
                                go2MyOrder(orderId);
                            }
                        })
        );
    }

    /**
     * 构建新建订单时的POST请求的body
     *
     * @return
     */
    private String buildNewOrderJsonBody() {
        NewOrderBodyBean newOrderBodyBean = new NewOrderBodyBean();
        newOrderBodyBean.setNotes("少油微辣");
        newOrderBodyBean.setRestaurant_info_id(Config.RESTAURANT_ID);
        newOrderBodyBean.setStatus(0);
        newOrderBodyBean.setTables_id(1);
        newOrderBodyBean.setUser_info_uid(UserProfile.getInstance().getUid());

        NewOrderBodyBean.DetailsBean detailsBean;
        List<NewOrderBodyBean.DetailsBean> detailsBeanList = new ArrayList<>();
        //SparseArray是一个HashMap...
        for (int i = 0; i < selectedList.size(); i++) {
            GoodsItem item = selectedList.valueAt(i);
            detailsBean = new NewOrderBodyBean.DetailsBean();
            detailsBean.setGoods_id(item.id);
            detailsBean.setStatus(0);
            detailsBean.setQuantity(item.count);
            detailsBeanList.add(detailsBean);
        }

        newOrderBodyBean.setDetails(detailsBeanList);

        return new Gson().toJson(newOrderBodyBean);
    }

    /**
     * 跳转到我的订单页面
     */
    private void go2MyOrder(@NonNull int orderId) {
//        Bundle bundle = new Bundle();
//        bundle.putInt(MyOrderActivity.ORDER_ID, orderId);
        IntentHelper.simpleJump(this, MyOrderActivity.class);

        //结束掉当前页面
        finish();
    }

    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id) {
        GoodsItem temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.count;
    }

    //根据类别Id获取属于当前类别的数量
    public int getSelectedGroupCountByTypeId(int typeId) {
        return groupSelect.get(typeId);
    }

    public void onTypeClicked(int typeId) {
        listView.setSelection(getSelectedPosition(typeId));
    }

    private int getSelectedPosition(int typeId) {
        int position = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).typeId == typeId) {
                position = i;
                break;
            }
        }
        return position;
    }

    @OnClick(R.id.bottom)
    void onShowBottomSheet() {
        if (bottomSheet == null) {
            bottomSheet = createBottomSheetView();
        }
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            if (selectedList.size() != 0) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }

    /**
     * 创建底部菜单栏
     *
     * @return
     */
    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        selectAdapter = new SelectAdapter(this, selectedList);
        rvSelected.setAdapter(selectAdapter);

        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(v -> {
            selectedList.clear();
            groupSelect.clear();
            update(true);
        });

        return view;
    }

    /**
     * 加载所有商品(菜品)信息
     */
    private void loadCookbook() {
        int restaurantId = Config.RESTAURANT_ID;
        getSubscription().add(
                DataObservable.goodsData(DataObservable.TYPE_NETWORK, restaurantId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CookbookResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                //数据加载失败,请稍后再试
                                AppEnv.getUserToast().simpleToast("数据加载失败: " + e.getMessage());

                                mockData();
                            }

                            @Override
                            public void onNext(CookbookResultBean cookbookResultBean) {
                                updateData(cookbookResultBean);
                            }
                        }));
    }

    /**
     * 模拟数据
     *
     * @return
     */
    private void mockData() {
        dataList.clear();
        typeList.clear();

        GoodsItem item = null;
        for (int i = 1; i < 15; i++) {
            for (int j = 1; j < 10; j++) {
                item = new GoodsItem(100 * i + j, Math.random() * 100, "模拟商品" + (100 * i + j), i, "模拟种类" + i);
                dataList.add(item);
            }
            typeList.add(item);
        }

        myAdapter.notifyDataSetChanged();
        typeAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据
     */
    private void updateData(@NonNull CookbookResultBean bean) {
        // FIXME: 17-5-7 当数据量过少时,左边的分类点击不会切换~

        dataList.clear();
        typeList.clear();

        GoodsItem item = null;
        List<CookbookResultBean.CategoriesBean> categories = bean.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            CookbookResultBean.CategoriesBean categoriesBean = categories.get(i);
            List<CookbookResultBean.CategoriesBean.ChildsBean> childs = categoriesBean.getChilds();
            for (int j = 0; j < childs.size(); j++) {
                CookbookResultBean.CategoriesBean.ChildsBean childsBean = childs.get(j);
                item = new GoodsItem(childsBean.getIdX(), childsBean.getReal_price(),
                        childsBean.getName(), categoriesBean.getCategory_info().getIdX(),
                        categoriesBean.getCategory_info().getName());
                dataList.add(item);
            }
            typeList.add(item);
        }

        myAdapter.notifyDataSetChanged();
        typeAdapter.notifyDataSetChanged();
    }

    /////// ------------------- GoodsItem ------------------- ///////
    // TODO: 17-5-6 后期可以考虑干掉GoodsItem,现在先做一个桥接
    public static class GoodsItem {
        public int id;
        public int typeId;
        public int rating;
        public String name;
        public String typeName;
        public double price;
        public int count;

        public GoodsItem(int id, double price, String name, int typeId, String typeName) {
            this.id = id;
            this.price = price;
            this.name = name;
            this.typeId = typeId;
            this.typeName = typeName;
            rating = new Random().nextInt(5) + 1;
        }
    }

    /////// ------------------- GoodsAdapter ------------------- ///////
    public static class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private ArrayList<GoodsItem> dataList;
        private ShoppingCartActivity mContext;
        private NumberFormat nf;
        private LayoutInflater mInflater;

        public GoodsAdapter(ArrayList<GoodsItem> dataList, ShoppingCartActivity mContext) {
            this.dataList = dataList;
            this.mContext = mContext;
            nf = NumberFormat.getCurrencyInstance();
            nf.setMaximumFractionDigits(2);
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_header_view, parent, false);
            }
            ((TextView) (convertView)).setText(dataList.get(position).typeName);
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            return dataList.get(position).typeId;
        }

        @Override
        public int getCount() {
            if (dataList == null) {
                return 0;
            }
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_goods, parent, false);
                holder = new ItemViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ItemViewHolder) convertView.getTag();
            }
            GoodsItem item = dataList.get(position);
            holder.bindData(item);
            return convertView;
        }

        private Animation getShowAnimation() {
            AnimationSet set = new AnimationSet(true);
            RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            set.addAnimation(rotate);
            TranslateAnimation translate = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF, 2f
                    , TranslateAnimation.RELATIVE_TO_SELF, 0
                    , TranslateAnimation.RELATIVE_TO_SELF, 0
                    , TranslateAnimation.RELATIVE_TO_SELF, 0);
            set.addAnimation(translate);
            AlphaAnimation alpha = new AlphaAnimation(0, 1);
            set.addAnimation(alpha);
            set.setDuration(500);
            return set;
        }

        private Animation getHiddenAnimation() {
            AnimationSet set = new AnimationSet(true);
            RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            set.addAnimation(rotate);
            TranslateAnimation translate = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF, 0
                    , TranslateAnimation.RELATIVE_TO_SELF, 2f
                    , TranslateAnimation.RELATIVE_TO_SELF, 0
                    , TranslateAnimation.RELATIVE_TO_SELF, 0);
            set.addAnimation(translate);
            AlphaAnimation alpha = new AlphaAnimation(1, 0);
            set.addAnimation(alpha);
            set.setDuration(500);
            return set;
        }

        class ItemViewHolder implements View.OnClickListener {
            private TextView name, price, tvAdd, tvMinus, tvCount;
            private GoodsItem item;
            private RatingBar ratingBar;

            public ItemViewHolder(View itemView) {
                name = (TextView) itemView.findViewById(R.id.tvName);
                price = (TextView) itemView.findViewById(R.id.tvPrice);
                tvCount = (TextView) itemView.findViewById(R.id.count);
                tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
                tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
                ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
                tvMinus.setOnClickListener(this);
                tvAdd.setOnClickListener(this);
            }

            public void bindData(GoodsItem item) {
                this.item = item;
                name.setText(item.name);
                ratingBar.setRating(item.rating);
                item.count = mContext.getSelectedItemCountById(item.id);
                tvCount.setText(String.valueOf(item.count));
                price.setText(nf.format(item.price));
                if (item.count < 1) {
                    tvCount.setVisibility(View.GONE);
                    tvMinus.setVisibility(View.GONE);
                } else {
                    tvCount.setVisibility(View.VISIBLE);
                    tvMinus.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onClick(View v) {
                ShoppingCartActivity activity = mContext;
                switch (v.getId()) {
                    case R.id.tvAdd: {
                        int count = activity.getSelectedItemCountById(item.id);
                        if (count < 1) {
                            tvMinus.setAnimation(getShowAnimation());
                            tvMinus.setVisibility(View.VISIBLE);
                            tvCount.setVisibility(View.VISIBLE);
                        }
                        activity.add(item, false);
                        count++;
                        tvCount.setText(String.valueOf(count));

                        // TODO: 17-5-6 暂时去掉这个抛物线动画,有BUG...
//                        int[] loc = new int[2];
//                        v.getLocationInWindow(loc);
//                        activity.playAnimation(loc);
                    }
                    break;
                    case R.id.tvMinus: {
                        int count = activity.getSelectedItemCountById(item.id);
                        if (count < 2) {
                            tvMinus.setAnimation(getHiddenAnimation());
                            tvMinus.setVisibility(View.GONE);
                            tvCount.setVisibility(View.GONE);
                        }
                        count--;
                        activity.remove(item, false);//activity.getSelectedItemCountById(item.id)
                        tvCount.setText(String.valueOf(count));

                    }
                    break;
                    default:
                        break;
                }
            }
        }
    }

    /////// ------------------- TypeAdapter ------------------- ///////
    public static class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
        public int selectTypeId;
        public ShoppingCartActivity activity;
        public ArrayList<GoodsItem> dataList;

        public TypeAdapter(ShoppingCartActivity activity, ArrayList<GoodsItem> dataList) {
            this.activity = activity;
            this.dataList = dataList;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvCount, type;
            private GoodsItem item;

            public ViewHolder(View itemView) {
                super(itemView);
                tvCount = (TextView) itemView.findViewById(R.id.tvCount);
                type = (TextView) itemView.findViewById(R.id.type);
                itemView.setOnClickListener(this);
            }

            public void bindData(GoodsItem item) {
                this.item = item;
                type.setText(item.typeName);
                int count = activity.getSelectedGroupCountByTypeId(item.typeId);
                tvCount.setText(String.valueOf(count));
                if (count < 1) {
                    tvCount.setVisibility(View.GONE);
                } else {
                    tvCount.setVisibility(View.VISIBLE);
                }
                if (item.typeId == selectTypeId) {
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                }

            }

            @Override
            public void onClick(View v) {
                activity.onTypeClicked(item.typeId);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GoodsItem item = dataList.get(position);

            holder.bindData(item);
        }

        @Override
        public int getItemCount() {
            if (dataList == null) {
                return 0;
            }
            return dataList.size();
        }


    }

    /////// ------------------- SelectAdapter ------------------- ///////
    public static class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
        private ShoppingCartActivity activity;
        private SparseArray<GoodsItem> dataList;
        private NumberFormat nf;
        private LayoutInflater mInflater;

        public SelectAdapter(ShoppingCartActivity activity, SparseArray<GoodsItem> dataList) {
            this.activity = activity;
            this.dataList = dataList;
            nf = NumberFormat.getCurrencyInstance();
            nf.setMaximumFractionDigits(2);
            mInflater = LayoutInflater.from(activity);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private GoodsItem item;
            private TextView tvCost, tvCount, tvAdd, tvMinus, tvName;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                tvCost = (TextView) itemView.findViewById(R.id.tvCost);
                tvCount = (TextView) itemView.findViewById(R.id.count);
                tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
                tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
                tvMinus.setOnClickListener(this);
                tvAdd.setOnClickListener(this);
            }

            public void bindData(GoodsItem item) {
                this.item = item;
                tvName.setText(item.name);
                tvCost.setText(nf.format(item.count * item.price));
                tvCount.setText(String.valueOf(item.count));
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tvAdd:
                        activity.add(item, true);
                        break;
                    case R.id.tvMinus:
                        activity.remove(item, true);
                        break;
                    default:
                        break;
                }
            }


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_selected_goods, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GoodsItem item = dataList.valueAt(position);
            holder.bindData(item);
        }

        @Override
        public int getItemCount() {
            if (dataList == null) {
                return 0;
            }
            return dataList.size();
        }


    }

    /////// ------------------- DividerDecoration ------------------- ///////
    public static class DividerDecoration extends RecyclerView.ItemDecoration {

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };
        private Drawable mDivider;

        public DividerDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            if (getOrientation(parent) == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (getOrientation(parent) == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }

        private int getOrientation(RecyclerView parent) {
            LinearLayoutManager layoutManager;
            try {
                layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            } catch (ClassCastException e) {
                throw new IllegalStateException("DividerDecoration can only be used with a " +
                        "LinearLayoutManager.", e);
            }
            return layoutManager.getOrientation();
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int recyclerViewTop = parent.getPaddingTop();
            final int recyclerViewBottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = Math.max(recyclerViewTop, child.getBottom() + params.bottomMargin);
                final int bottom = Math.min(recyclerViewBottom, top + mDivider.getIntrinsicHeight());
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int recyclerViewLeft = parent.getPaddingLeft();
            final int recyclerViewRight = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = Math.max(recyclerViewLeft, child.getRight() + params.rightMargin);
                final int right = Math.min(recyclerViewRight, left + mDivider.getIntrinsicHeight());
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
