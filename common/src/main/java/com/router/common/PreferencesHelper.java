package com.router.common;

import com.router.common.utils.SPUtils;

public class PreferencesHelper {
    private static final String CACHE_PROTECTED = "protected_cache";
    private static final String CACHE_CLEAR = "clear_cache";
    //这是受保护的持久化缓存
    private static SPUtils protectedCache = SPUtils.getInstance(CACHE_PROTECTED);
    //这是可清除的持久化缓存
    private static SPUtils clearCache = SPUtils.getInstance(CACHE_CLEAR);

    public static SPUtils getProtectedCache() {
        return protectedCache;
    }

    public static SPUtils getClearCache() {
        return clearCache;
    }
}
