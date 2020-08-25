package com.cq.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.elvishew.xlog.XLog;
import com.router.common.ConfigConstants;
import com.router.common.RouteHelper;
import com.router.common.base.BaseActivity;
import com.router.common.http.RetrofitUtil;
import com.router.common.utils.ToastUtils;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Route(path = ConfigConstants.HOME_MAIN)
public class MainActivity extends BaseActivity {
    @Autowired
    String key;
    @BindView(R2.id.btn1)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        setStatusBarDark();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showMessage(activity,"Hello "+ key);
                request();
            }
        });
        String allPath = RouteHelper.getInstance().getAllPath();
        XLog.d(allPath);
    }
    private void request(){
        RetrofitUtil.get().getApiService().getNews("top")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        XLog.d( s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        XLog.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
