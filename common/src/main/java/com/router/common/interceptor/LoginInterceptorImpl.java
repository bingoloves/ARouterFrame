package com.router.common.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.router.common.RouteHelper;
import com.router.common.utils.ToastUtils;
import com.router.common.utils.Utils;

/**
 * Created by bingo on 2020/8/25 0025.
 * 全局跳转拦截器 登录校验 注意：只有通过navigation跳转的页面才会捕获到
 */
@Interceptor(name = "login", priority = 6)
public class LoginInterceptorImpl implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        final String path = postcard.getPath();
        RouteHelper.getInstance().addToHistory(path);
        ToastUtils.showMessage(Utils.getApp(),path);
        callback.onContinue(postcard);
//        LogUtils.d(path);
//        boolean isLogin = false;
//        if (isLogin) { //如果已经登录不拦截
//            callback.onContinue(postcard);
//        } else {  // 如果没有登录
//            switch (path) {
//                // 不需要登录的直接进入这个页面
////                case ConfigConstants.LOGIN_PATH:
////                case ConfigConstants.FIRST_PATH:
////                    callback.onContinue(postcard);
////                    break;
//                default:
//                    //需要登录的直接拦截下来
//                    callback.onInterrupt(null);
//                    break;
//            }
//        }
    }

    @Override
    public void init(Context context) {
//        LogUtils.v("路由登录拦截器初始化成功"); //只会走一次
    }
}
