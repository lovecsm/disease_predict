package com.example.asus.helloworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by ASUS on 2018/6/22.
 */

public class AskFragment extends Fragment {
    private WebView webView;
    private ProgressBar web_progress_bar;
    public static final String ENTRANCE_URL_LOGIN = "https://m.myzx.cn/user/login/index/referer"; //登陆url
    public static final String ENTRANCE_URL_HOME = "https://m.myzx.cn/wenzhen/question/createquestiondisplay"; //登陆后调整的url

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ask_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        webView = (WebView) getView().findViewById(R.id.web_view_ask_doctor);
        web_progress_bar = (ProgressBar) activity.findViewById(R.id.progress_bar);
        initWebView();
    }

    private void initWebView() {
        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(ENTRANCE_URL_HOME); //获取cookie
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    web_progress_bar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    web_progress_bar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    web_progress_bar.setProgress(newProgress);//设置进度值
                }
            }
        });
        if (TextUtils.isEmpty(CookieStr)) {
            webView.loadUrl(ENTRANCE_URL_LOGIN);
        } else {
            webView.loadUrl(ENTRANCE_URL_HOME);
        }
    }

    boolean isExit;

    public void goBack() {
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            isExit = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                getActivity().finish();
            }
        }
    }
}
