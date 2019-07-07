package com.safar724test.app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.safar724test.app.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
//    private EditText urlEditText;
    private boolean clickedOnce = false;
    private String requestHeaders;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
//        webView.loadUrl("file:///android_asset/test.htm");
        webView.loadUrl("https://mob.safar724.com");
//        webView.loadUrl("https://mob.safar724.com");
    }

//    public void goToUrl(View view) {
//        webView.loadUrl(urlEditText.getText().toString());
//    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
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
                    Toast.makeText(getApplicationContext(), requestHeaders, Toast.LENGTH_LONG).show();
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
