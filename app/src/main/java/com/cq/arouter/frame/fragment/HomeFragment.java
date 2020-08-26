package com.cq.arouter.frame.fragment;

import android.graphics.PointF;

import com.cq.arouter.frame.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.router.common.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/26 0026.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.scaleImageView)
    SubsamplingScaleImageView scaleImageView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
//      设置最小缩放比，默认是1
        scaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        scaleImageView.setMinScale(1.5f);
//        scaleImageView.setImage(ImageSource.uri("本地路径"));
        scaleImageView.setImage(ImageSource.resource(R.mipmap.big));
//      设置默认缩放比和初始显示位置
        scaleImageView.setScaleAndCenter(1.5f, new PointF(0, 0));
    }

    @Override
    protected void lazyLoad() {

    }
}
