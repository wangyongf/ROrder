package com.yongf.rorder.component.dialog;

import android.app.AlertDialog;
import android.content.Context;

/**
 * 原生AlertDialog
 *
 * @author dingguo@wacai.com
 * @since 2017/3/8
 */

public class NativeDialogHelper {

    // TODO: 17-3-14 添加真正的CustomDialog，布局+逻辑

    /**
     * 显示原生AlertDialog，只是为了显示通知
     *
     * @param context           上下文
     * @param title             对话框标题
     * @param messageResId      对话框文字描述资源ID
     * @param positiveButtonMsg 底部按钮描述
     */
    public static void nativeDialog(Context context, String title, int messageResId, String positiveButtonMsg) {
        nativeDialog(context, title, context.getString(messageResId), positiveButtonMsg);
    }

    /**
     * 显示原生AlertDialog，只是为了显示通知
     *
     * @param context           上下文
     * @param title             对话框标题
     * @param message           对话框文字描述
     * @param positiveButtonMsg 底部按钮描述
     */
    public static void nativeDialog(Context context, String title, String message, String positiveButtonMsg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonMsg, null)
                .create();
        alertDialog.show();
    }

    /**
     * 显示原生AlertDialog，只是为了显示一则通知，不可取消
     *
     * @param context           上下文
     * @param title             对话框标题
     * @param messageResId      对话框文字描述
     * @param positiveButtonMsg 底部按钮文字描述
     * @param cancelable        对话框是否可以取消
     */
    public static void nativeDialog(Context context, String title, int messageResId, String positiveButtonMsg, boolean cancelable) {
        nativeDialog(context, title, context.getString(messageResId), positiveButtonMsg, cancelable);
    }

    /**
     * 显示原生AlertDialog，只是为了显示一则通知，不可取消
     *
     * @param context           上下文
     * @param title             对话框标题
     * @param message           对话框文字描述
     * @param positiveButtonMsg 底部按钮文字
     * @param cancelable        对话框是否可以取消
     */
    public static void nativeDialog(Context context, String title, String message, String positiveButtonMsg, boolean cancelable) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonMsg, null)
                .create();
        alertDialog.setCancelable(cancelable);
        alertDialog.show();
    }
}
