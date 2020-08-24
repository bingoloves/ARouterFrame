package com.cq.arouter.frame;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.router.common.utils.AppConfig;

/**
 * Created by Administrator on 2020/8/24 0024.
 */

public class App extends Application{
    private boolean isDebug = BuildConfig.DEBUG;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AppConfig.getInstance().init(this);
        initARouter();
    }
    private void initARouter() {
        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
