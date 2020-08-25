package com.cq.arouter.frame.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.cq.arouter.frame.R;
import com.router.common.ConfigConstants;
import com.router.common.base.BaseActivity;

import butterknife.BindView;

@Route(path = ConfigConstants.MAIN_MAIN)
public class MainActivity extends BaseActivity {

    @Autowired
    String key;
    @BindView(R.id.tv_title)
    TextView titleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        setStatusBarDark();
        //TextView titleTv = findViewById(R.id.tv_title);
        titleTv.setText(key);
    }
}
