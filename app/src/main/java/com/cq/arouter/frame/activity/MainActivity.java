package com.cq.arouter.frame.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.cq.arouter.frame.R;
import com.cq.arouter.frame.fragment.HomeFragment;
import com.cq.arouter.frame.fragment.WebFragment;
import com.cq.arouter.frame.navigation.BottomNavigationItem;
import com.cq.arouter.frame.navigation.BottomNavigationView;
import com.router.common.ConfigConstants;
import com.router.common.base.BaseActivity;
import com.router.common.utils.FragmentUtils;

import butterknife.BindView;

@Route(path = ConfigConstants.MAIN_MAIN)
public class MainActivity extends BaseActivity {
    @Autowired
    String key;
    @BindView(R.id.main_root)
    LinearLayout rootLayout;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigationView;

    private static final String CURRENT_TAB_INDEX = "current_tab_index";

    private String[] titles = {"录音","收藏","记录"};
    private int[] image = {R.drawable.ic_mic, R.drawable.ic_favorite, R.drawable.ic_book};
    private int currentIndex = 0;
    private Fragment[] fragments = new Fragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarDark();
        initBottomNavigationView();
        initFragments(savedInstanceState);
    }

    /**
     * 初始化碎片管理
     * @param savedInstanceState
     */
    private void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(CURRENT_TAB_INDEX);
        } else {
            fragments[0] = new HomeFragment();
            fragments[1] = new WebFragment();
            fragments[2] = new HomeFragment();
        }
        FragmentUtils.add(getSupportFragmentManager(), fragments, R.id.container, currentIndex);
    }

    /**
     * 初始化底部設置
     */
    private void initBottomNavigationView() {
        if (bottomNavigationView != null) {
            bottomNavigationView.isWithText(true);
//            bottomNavigationView.activateTabletMode();
//            rootLayout.setOrientation(LinearLayout.HORIZONTAL);
            bottomNavigationView.isColoredBackground(false);
            bottomNavigationView.disableShadow();
            bottomNavigationView.setTextActiveSize(getResources().getDimension(R.dimen.bottom_navigation_text_size_active));
            bottomNavigationView.setTextInactiveSize(getResources().getDimension(R.dimen.bottom_navigation_text_size_active));
            bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.colorPrimary));
            bottomNavigationView.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/方正小篆.ttf"));
            addBottomItems();
            bottomNavigationView.setOnBottomNavigationItemClickListener(index -> {
                currentIndex = index;
                FragmentUtils.showHide(currentIndex, fragments);
            });
        }
    }

    private void addBottomItems(){
        for (int i = 0; i<titles.length; i++) {
            String title = titles[i];
            BottomNavigationItem  bottomNavigationItem =  new BottomNavigationItem(title, ContextCompat.getColor(this, R.color.colorPrimary), image[i]);
            bottomNavigationView.addTab(bottomNavigationItem);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_TAB_INDEX, currentIndex);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        WebFragment webFragment = (WebFragment) fragments[1];
        if (webFragment != null) {
            webFragment.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
