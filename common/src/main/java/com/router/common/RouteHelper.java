package com.router.common;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/8/25 0025.
 * 路由跳转单例
 */

public class RouteHelper {
    private RouteHelper (){}
    private static class RouteHelperHolder{
        private static RouteHelper routeHelper = new RouteHelper();
    }
    public static RouteHelper getInstance(){
        return RouteHelperHolder.routeHelper;
    }

    /**
     * 页面跳转的历史记录
     */
    private List<String> historyList = new ArrayList<>();
    /**
     * 应用缓存白名单 在白名单的页面不需要鉴权和登录等特殊条件
     */
    private List<String> whiteList = new ArrayList<>();

    /**
     * 添加记录到历史记录中
     * @param path
     */
    public void addToHistory(String path){
        historyList.add(path);
    }

    /**
     * 从历史记录中异常
     * @param path
     */
    public void removeFromHistory(String path){
        historyList.remove(path);
    }

    /**
     * 添加到白名单
     * @param path
     */
    public void addToWhite(String path){
        if (!whiteList.contains(path))whiteList.add(path);
    }

    /**
     * 是否包含在白名单中
     * @param path
     * @return
     */
    public boolean isWhiteListPath(String path){
        return whiteList.contains(path);
    }
    /**
     * 获取当前跳转的path路径历史记录
     * @return
     */
    public String getAllPath(){
        return TextUtils.join(";",historyList);
    }
}
