package com.cq.arouter.frame;

import com.router.common.BaseApplication;

/**
 * Created by Administrator on 2020/8/24 0024.
 */

public class App extends BaseApplication{

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
