package com.cq.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.router.common.base.BaseActivity;
import com.router.common.utils.ToastUtils;

@Route(path = "/home/main")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        setStatusBarDark();
        Button button = findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showMessage(activity,"Hello world");
            }
        });
    }
}
