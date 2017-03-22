package com.yongf.rorder.widget.filtermenu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yongf.rorder.R;

import java.util.HashMap;
import java.util.Map;

/**
 * FilterMenu的适配器
 */
public abstract class FilterMenuAdapter extends PagerAdapter {

    private Context mContext;
    private Map<Integer, View> mTabMenu = new HashMap<>();
    private OnOpacityBgClickListener mOnOpacityBgClickListener = null;

    public FilterMenuAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mTabMenu.containsKey(position)) {
            return mTabMenu.get(position);
        } else {
            View opacityBg = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_tabmenu_opacity_bg,
                    null);
            opacityBg.setOnClickListener(view -> {
                if (mOnOpacityBgClickListener != null) {
                    mOnOpacityBgClickListener.onOpacityBgClick(position);
                }
            });

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(getTabMenuContent(position));
            linearLayout.addView(opacityBg);

            container.addView(linearLayout);
            mTabMenu.put(position, linearLayout);

            return linearLayout;
        }
    }

    /**
     * 获取Tab Menu
     *
     * @param position
     *
     * @return
     */
    public abstract View getTabMenuContent(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(getTabMenuContent(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 获取顶部Tab
     *
     * @param position
     *
     * @return
     */
    public abstract View getTab(int position);

    /**
     * 获取FilterMenux下面的内容View，常见的是ListView, RecyclerView等
     *
     * @return
     */
    public abstract View getContent();

    public OnOpacityBgClickListener getOnOpacityBgClickListener() {
        return mOnOpacityBgClickListener;
    }

    public void setOnOpacityBgClickListener(OnOpacityBgClickListener onOpacityBgClickListener) {
        mOnOpacityBgClickListener = onOpacityBgClickListener;
    }

    public interface OnOpacityBgClickListener {
        void onOpacityBgClick(int position);
    }
}
