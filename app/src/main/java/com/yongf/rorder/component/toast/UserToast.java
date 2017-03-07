package com.yongf.rorder.component.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yongf.rorder.R;

/**
 * 用户吐司
 *
 * @author dingguo@wacai.com
 * @since 2017/3/7
 */

public class UserToast {

    private Context mContext;
    private Toast mToast;

    public UserToast(Context context) {
        mContext = context;
    }

    /**
     * 弹吐司，指定文字，默认显示SHORT
     *
     * @param msg 吐司文字
     */
    public void simpleToast(String msg) {
        simpleToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 弹吐司，指定文字和显示时长
     *
     * @param msg    描述文字
     * @param length 吐司显示时长
     */
    public void simpleToast(String msg, int length) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, length);
        } else {
            mToast.setText(msg);
            mToast.setDuration(length);
        }
        mToast.show();
    }

    /**
     * 隐藏吐司
     */
    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 使用默认布局创建吐司
     *
     * @param msg    吐司文字
     * @param length 吐司显示时长
     */
    public void toast(String msg, int length) {
        Toast toast = new Toast(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_toast_default, null);
        ((TextView) view.findViewById(R.id.tv_desc)).setText(msg);
        toast.setView(view);
        toast.setDuration(length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
