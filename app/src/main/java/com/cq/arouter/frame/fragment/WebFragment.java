package com.cq.arouter.frame.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cq.arouter.frame.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.router.common.base.BaseFragment;
import butterknife.BindView;
import im.delight.android.webview.AdvancedWebView;

/**
 * Created by Administrator on 2020/8/26 0026.
 */

public class WebFragment extends BaseFragment implements AdvancedWebView.Listener {

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.webview)
    AdvancedWebView webView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initView() {
        initRefresh();
        webView.setListener(getActivity(),this);
        webView.loadUrl("http://www.baidu.com/");
//        webView.setGeolocationEnabled(true);
//        webView.addHttpHeader("X-Requested-With", "My wonderful app");
//        webView.addPermittedHostname("example.org");
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //多窗口支持
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                AdvancedWebView newWebView = new AdvancedWebView(getContext());
                //myParentLayout.addView(newWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                return true;
            }
        });
    }

    private void initRefresh() {
        //下拉刷新头部view设置
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.ic_arrow);
        refreshLayout.setHeaderView(headerView);
        //上拉加载底部view设置
        LoadingView loadingView = new LoadingView(getContext());
        refreshLayout.setBottomView(loadingView);
        refreshLayout.setHeaderHeight(80);
        //下拉刷新上拉加载事件监听
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(() -> refreshLayout.finishRefreshing(),2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(() -> refreshLayout.finishLoadmore(),2000);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }
    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        webView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        webView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }
}
