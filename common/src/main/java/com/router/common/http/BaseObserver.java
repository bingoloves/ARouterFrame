package com.router.common.http;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.WindowManager;

import com.elvishew.xlog.XLog;
import com.router.common.utils.NetworkUtil;
import com.router.common.utils.Utils;

import org.json.JSONException;
import java.lang.reflect.Field;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 针对普通对象
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private Disposable d;
    private ProgressDialog progressDialog;
    private Handler handler;
    //持续时间最小2000ms保证体验效果
    private static final int MIN_TIME = 2000;
    private long startTime = 0;
    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        Context context = Utils.getApp();
        if (!NetworkUtil.isNetworkConnected(context)) {
            onNetworkError();
            if (d.isDisposed()) {
                d.dispose();
            }
        }
        if (progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(true);
            progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            progressDialog.setCanceledOnTouchOutside(true);
        }
        progressDialog.show();
        handler = new Handler();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onNext(final T data) {
        onSuccess(data);
    }

    @Override
    public void onError(final Throwable e) {
        onFailure(exceptionHandler(e));
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
        handleDialogEvent(new Runnable() {
            @Override
            public void run() {
                hideDialog();
            }
        });
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(String err);

    /**
     * 本地网络不可用时异常,会回调这个方法
     */
    public void onNetworkError(){
         hideDialog();
     }

    /**
     * 处理dialog 显示效果
     */
    private void handleDialogEvent(Runnable runnable){
         long endTime = System.currentTimeMillis();
         long timeDiff = endTime - startTime;
         //XLog.e("timeDiff = " + timeDiff);
         if (timeDiff > MIN_TIME){
             handler.post(runnable);
         } else {
             handler.postDelayed(runnable,MIN_TIME - timeDiff);
         }
    }

    /**
     * 隐藏dialog
     */
    private void hideDialog(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    /**
     * 异常处理
     * @param e
     * @return 返回错误信息
     */
    private String exceptionHandler(Throwable e){
        String errorMsg = "未知错误";
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不可用";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "请求网络超时";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorMsg = convertStatusCode(httpException);
        } else if (e instanceof ParseException || e instanceof JSONException
                || e instanceof JSONException) {
            errorMsg =  "数据解析错误";
        }
        return errorMsg;
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg =  "服务器处理请求出错";
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg =  "服务器无法处理请求";
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg =  "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
