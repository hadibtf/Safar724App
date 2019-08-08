package com.safar724test.app.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.safar724test.app.CustomDialog;
import com.safar724test.app.G;
import com.safar724test.app.R;

import java.util.HashMap;

public class NotificationWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_web_view);

        WebView webView = findViewById(R.id.web_view_notif);

        Intent intent = getIntent();
        String intendedUrl = intent.getStringExtra("intendedUrl");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setGeolocationEnabled(true);
        webView.canGoBack();
        webView.loadUrl(intendedUrl);

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
                ConstraintLayout constraintLayout = findViewById(R.id.loading_view_notif);
                constraintLayout.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(webViewClient);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
        finish();
    }

    private void showAlertDialog() {
        CustomDialog.CustomDialogButtonClick customDialogButtonClick =
                () -> Toast.makeText(this, "Clicked!", Toast.LENGTH_LONG).show();
        CustomDialog customDialog = new CustomDialog(this, customDialogButtonClick);
        customDialog.show();
    }
}