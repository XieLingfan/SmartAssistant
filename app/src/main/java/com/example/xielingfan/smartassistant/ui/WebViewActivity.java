package com.example.xielingfan.smartassistant.ui;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.utils.L;

import rx.internal.schedulers.EventLoopsScheduler;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.ui
 * 文件名:  WebViewActivity
 * 创建者： XieLingfan
 * 创建时间:2018/7/26 19:03
 * 描述：   新闻详情
 */

public class WebViewActivity extends BaseActivity {

    //进度
    private ProgressBar mProgressBar;
    //网页
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
    }

    //初始化View
    private void initView() {

        mProgressBar = (ProgressBar)findViewById(R.id.mProgressBar);
        mWebView = (WebView) findViewById(R.id.mWebView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");


        //设置标题
        getSupportActionBar().setTitle(title);

        //加载网页

        //支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);
        //本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }

                return true;
            }
        });

    }

    public class WebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            }

            super.onProgressChanged(view, newProgress);
        }
    }
}
