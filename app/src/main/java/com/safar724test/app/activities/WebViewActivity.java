package com.safar724test.app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.safar724test.app.R;

import java.util.HashMap;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    //    private EditText urlEditText;
    private boolean clickedOnce = false;
    private boolean firstLoadingDone = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
//        webView.loadUrl("file:///android_asset/test.htm");
        Intent intent = getIntent();
        String intendedUrl = intent.getStringExtra("intendedUrl");
        if (intendedUrl != null) {
            webView.loadUrl(intendedUrl);
            return;
        }
        webView.loadUrl("https://safar724.com");
//        webView.loadUrl("https://mob.safar724.com");
    }

//    public void goToUrl(View view) {
//        webView.loadUrl(urlEditText.getText().toString());
//    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        //Set Activity Layout
        setContentView(R.layout.activity_main);

        //Initialize Views
        webView = findViewById(R.id.web_view);

//        urlEditText = findViewById(R.id.urlEditText);

        //Set the webView required settings
        webView.getSettings().setJavaScriptEnabled(true);

        //Loads the default Cache Policy
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.getSettings().setGeolocationEnabled(true);

        //Allows WebView to go back
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
                if (!firstLoadingDone) {
                    firstLoadingDone = true;
                    View loadingView = findViewById(R.id.loading_view);
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                    loadingView.startAnimation(anim);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            loadingView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    loadingView.setVisibility(View.INVISIBLE);
                }
//                if (url.contains("faq")) {
//                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class).putExtra("header", requestHeaders));
//                }
                super.onPageFinished(view, url);
            }
        });
//        urlEditText.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) urlEditText.setText(getResources().getString(R.string.https));
//        });
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
