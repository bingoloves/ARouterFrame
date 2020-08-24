package com.cq.arouter.frame.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cq.arouter.frame.R;
import com.router.common.base.BaseActivity;
@Route(path = "/app/main")
public class MainActivity extends BaseActivity {
    @Autowired
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        ARouter.getInstance().inject(this);
        setStatusBarDark();
        TextView titleTv = findViewById(R.id.tv_title);
        titleTv.setText(key);
    }
}
