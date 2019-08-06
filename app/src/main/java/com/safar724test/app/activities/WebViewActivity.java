package com.safar724test.app.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.safar724test.app.G;
import com.safar724test.app.R;

import java.util.HashMap;

public class WebViewActivity extends Activity {
    private WebView webView;
    private boolean clickedOnce = false;
    private boolean firstLoadingDone = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        G g = (G) getApplication();

        if (g.isNetworkAvailable()) init();


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setGeolocationEnabled(true);
        webView.canGoBack();
        webView.loadUrl("https://mob.safar724.com/");
        WebViewClient webViewClient = new WebViewClient() {

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.contains("faq")) {
                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                }
                view.loadUrl(request.getUrl().toString(), getHeaders());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("faq")) {
                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                }
                view.loadUrl(url, getHeaders());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ConstraintLayout constraintLayout = findViewById(R.id.loading_view);
                constraintLayout.setVisibility(View.GONE);
                showAlertDialog();
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

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String versionCode = String.valueOf(packageInfo != null ? packageInfo.versionCode : 0);
        String versionName = packageInfo.versionName;
        G g = (G) getApplication();
        String fcmToken = g.getNotifToken();

        headerExtras.put("X-APP-TOKEN", fcmToken);
        headerExtras.put("X-APP-VERSION-CODE", versionCode);
        headerExtras.put("X-APP-VERSION-NAME", versionName);

        return headerExtras;
    }

    private void showAlertDialog() {
        final View popUpView = LayoutInflater.from(WebViewActivity.this).inflate(R.layout.custom_dialog, null);
        final PopupWindow popupWindow = new PopupWindow(popUpView);
        popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
    }
}

