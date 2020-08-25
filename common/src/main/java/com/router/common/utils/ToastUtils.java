package com.router.common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


/**
 * ToastUtils
 */
public class ToastUtils {
    private static Toast toast = null;
    private static Handler mainHandler = new Handler(Looper.getMainLooper());
    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param act
     * @param msg
     */
    public static void showMessage(final Context act, final String msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param act
     * @param msg
     */
    public static void showMessageLong(final Context act, final String msg) {
        showMessage(act, msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param act
     * @param msg
     */
    public static void showMessage(final Context act, final int msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param act
     * @param msg
     */
    public static void showMessageLong(final Context act, final int msg) {
        showMessage(act, msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息
     *
     * @param act
     * @param msg
     * @param len
     */
    public static void showMessage(final Context act, final int msg, final int len) {
        if (isMainThread()){
            toast = Toast.makeText(act, msg + "", len);
            toast.show();
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(act, msg + "", len);
                    toast.show();
                }
            });
        }
    }

    /**
     * Toast发送消息
     *
     * @param act
     * @param msg
     * @param len
     */
    public static void showMessage(final Context act, final String msg, final int len) {
        if (isMainThread()){
            toast = Toast.makeText(act, msg, len);
            toast.show();
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(act, msg + "", len);
                    toast.show();
                }
            });
        }
    }

    /**
     * 关闭当前Toast
     */
    public static void cancelCurrentToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 判断当前是否在主线程
     * @return
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}