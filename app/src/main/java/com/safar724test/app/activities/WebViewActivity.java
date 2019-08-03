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
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.safar724test.app.G;
import com.safar724test.app.R;

import java.util.HashMap;

public class WebViewActivity extends Activity {
    private WebView webView;
    private boolean clickedOnce = false;
    private boolean firstLoadingDone = false;
    private G g;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        g = (G) getApplication();

        if (!g.isNetworkAvailable()) init();

        Intent intent = getIntent();
        String intendedUrl = intent.getStringExtra("intendedUrl");
        if (intendedUrl != null) {
            webView.loadUrl(intendedUrl);
            return;
        }

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

    private HashMap<String, String> getHeaders()
    {
        HashMap<String, String> headerExtras = new HashMap<>();

//        TelephonyManager telephonyManager = (TelephonyManager) this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(
//                        WebViewActivity.this,
//                        new String[]{Manifest.permission.READ_PHONE_STATE},
//                        1);
//            }
//        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String versionCode = String.valueOf(packageInfo.versionCode);
        String versionName = packageInfo.versionName;
//        String phone = telephonyManager.getLine1Number();
        String fcmToken = g.getNotifToken();

        headerExtras.put("X-APP-TOKEN", fcmToken);
        if (versionCode != null) headerExtras.put("X-APP-VERSION-CODE", versionCode);
        if (versionName != null) headerExtras.put("X-APP-VERSION-NAME", versionName);
//        if (phone != null) headerExtras.put("X-APP-USER-PHONE", phone);

        return headerExtras;
    }
}

