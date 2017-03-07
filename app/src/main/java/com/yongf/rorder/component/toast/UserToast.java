package com.yongf.rorder.component.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * 用户吐司
 *
 * @author dingguo@wacai.com
 * @since 2017/3/7
 */

public class UserToast {

    private Context mContext;

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
        Toast.makeText(mContext, msg, length).show();
    }
}
