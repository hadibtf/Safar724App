package com.safar724test.app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class WebViewActivity extends AppCompatActivity {
    private boolean clickedOnce = false;
    WebView webView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        initWebView(webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(WebView webView) {
        webView.loadUrl("https://mob.safar724.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setGeolocationEnabled(true);
        webView.canGoBack();

        //Customize WebViewClient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                HashMap<String, String> headerExtras = new HashMap<>();
                headerExtras.put("ANDROID_HEADER_EXTRA", "HELLO_WORLD");
                view.loadUrl(url, headerExtras);
                //When a new url is loaded, checks if the url matches the the preferred url.
                if (url.contains("faq")) {
                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                requestHeaders = request.getRequestHeaders().toString();
//                return super.shouldInterceptRequest(view, request);
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                if (url.contains("faq")) {
//                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class).putExtra("header", requestHeaders));
//                }
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (clickedOnce) {
            super.onBackPressed();
            return;
        }
        clickedOnce = true;
        Toast.makeText(this, "Press Back again to exit!", Toast.LENGTH_LONG).show();
        webView.goBack();
        Handler handler = new Handler();
        handler.postDelayed(() -> clickedOnce = false, 2000);
    }
}
