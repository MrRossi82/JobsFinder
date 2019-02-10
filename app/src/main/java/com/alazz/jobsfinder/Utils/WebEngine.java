package com.alazz.jobsfinder.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

@SuppressWarnings("unused")
public class WebEngine {

    private final WebView mWebView;
    private Activity mActivity;
    private final Context mContext;


    public WebEngine(WebView webView, Fragment fragment) {
        mWebView = webView;
        this.mContext = fragment.requireActivity();
    }

    public WebEngine(WebView webView, Context context) {
        this.mWebView = webView;
        mContext = context;
    }


    @SuppressLint("SetJavaScriptEnabled")

    public void initWebView() {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCachePath(mContext.getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // load online by default
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setTextZoom(100);


    }

    public void initListeners(final WebListener webListener) {

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String webUrl) {

                onLoadPageURL(webUrl);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webListener.onStart();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webListener.onLoaded();
            }

        });


    }

    public void onLoadPageURL(String url) {

        mWebView.loadUrl(url);

    }

    public void onLoadPageData(String date) {

        mWebView.loadData(date, "text/html; charset=UTF-8", null);


    }


    private void invokeNativeApp(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mActivity.startActivity(intent);
    }




}
