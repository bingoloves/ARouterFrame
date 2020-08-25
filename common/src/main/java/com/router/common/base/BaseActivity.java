package com.router.common.base;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.router.common.statusbar.StatusBarUtil;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    protected Activity activity;
    public Handler mainHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        activity = this;
        mainHandler = new Handler();
    }

    @Override
    public void setContentView(int layoutResID) {
        /**setBlackWhiteScreen();*/
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
        initStatusBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        unbinder = ButterKnife.bind(this,view);
    }
    /**
     * 设置黑白屏效果
     */
    private void setBlackWhiteScreen(){
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE,paint);
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        StatusBarUtil.setTranslucentStatus(this);//设置状态栏透明
    }

    /**
     * 状态栏白色时 设置深色模式
     */
    protected void setStatusBarDark(){
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }
    /**
     * 取消状态栏深色模式
     */
    protected void setStatusBarLight(){
        StatusBarUtil.setStatusBarDarkTheme(this, false);
    }

    /**
     * 简单统一调用自定义 Toast
     */
    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       if(unbinder != null){
           unbinder.unbind();
       }
    }
}
