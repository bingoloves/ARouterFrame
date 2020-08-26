package com.cq.arouter.frame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cq.arouter.frame.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.router.common.ConfigConstants;
import com.router.common.base.BaseActivity;
import com.router.common.statusbar.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by bingo on 2020/8/8.
 */
@Route(path = ConfigConstants.MAIN_SPLASH)
public class SplashActivity extends BaseActivity {

    @BindView(R.id.kenBurnsView)
    KenBurnsView kenBurnsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);//设置全屏
        kenBurnsView.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }
            @Override
            public void onTransitionEnd(Transition transition) {
                ARouter.getInstance().build(ConfigConstants.MAIN_MAIN).withString("key","bingo").navigation();
                finish();
            }
        });
        Interpolator interpolator = new AccelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(5000, interpolator);
        kenBurnsView.setTransitionGenerator(generator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        kenBurnsView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        kenBurnsView.pause();
    }
}
