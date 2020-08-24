package com.cq.arouter.frame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cq.arouter.frame.R;
import com.router.common.base.BaseActivity;
import com.router.common.statusbar.StatusBarUtil;

/**
 * Created by bingo on 2020/8/8.
 */
@Route(path = "/app/splash")
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);//设置全屏
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ARouter.getInstance().build("/app/main").withString("key","bingo").navigation();
                ARouter.getInstance().build("/home/main").withString("key","bingo").navigation();
                finish();
//                startActivity(new Intent(activity,MainActivity.class));
//                finish();
            }
        },1200);
    }
}
