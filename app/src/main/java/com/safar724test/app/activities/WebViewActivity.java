package com.safar724test.app.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
//        HashMap<String, String> headerExtras = new HashMap<>();
//        webView.loadUrl("https://safar724.com");
        webView.loadUrl("https://google.com/");
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Hello world!").setTitle("Error").setPositiveButton("yes",
//                (dialog, which) -> {
//                    dialog.dismiss();
//                    finish();
//                }).show();

    }

//    public void goToUrl(View view) {
//        webView.loadUrl(urlEditText.getText().toString());
//    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_view);

//        urlEditText = findViewById(R.id.urlEditText);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.getSettings().setGeolocationEnabled(true);

        webView.canGoBack();

        webView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        HashMap<String, String> headerExtras = new HashMap<>();
                        headerExtras.put("ANDROID_HEADER_EXTRA", "HELLO_WORLD");
                        view.loadUrl(request.getUrl().toString(), headerExtras);
                        return true;
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        HashMap<String, String> headerExtras = new HashMap<>();
                        headerExtras.put("ANDROID_HEADER_EXTRA", "HELLO_WORLD");
                        view.loadUrl(url, headerExtras);
                        return true;
                    }
                }
        );
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
