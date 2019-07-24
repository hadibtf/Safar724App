package com.safar724test.app.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

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
        Intent intent = getIntent();
        String intendedUrl = intent.getStringExtra("intendedUrl");
        if (intendedUrl != null) {
            webView.loadUrl(intendedUrl);
            return;
        }

        webView.loadUrl("https://google.com/");

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setGeolocationEnabled(true);
        webView.canGoBack();
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString(), getHeaders());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url, getHeaders());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ConstraintLayout constraintLayout = findViewById(R.id.loading_view);
                constraintLayout.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(webViewClient);
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

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headerExtras = new HashMap<>();
        String phone;
        TelephonyManager telephonyManager = (TelephonyManager) this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        WebViewActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1);
            }
        }
        phone = telephonyManager.getLine1Number();
        if (phone != null) headerExtras.put("PHONE", phone);
        headerExtras.put("AGENT", "Android");
        return headerExtras;
    }
}

