package com.safar724test.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText urlEditText;
    private boolean clickedOnce = false;
    private String requestHeaders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    System.out.println("----+: " + "failed");
                    return;
                }
                // Get new Instance ID token
                String token = task.getResult().getToken();
                System.out.println("----+: " + token);
            }

        });
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        init();
        webView.loadUrl("file:///android_asset/test.htm");
//        webView.loadUrl("https://mob.safar724.com");
    }

    public void goToUrl(View view) {
        webView.loadUrl(urlEditText.getText().toString());
    }

    private void init() {

        //Initialize Views
        webView = findViewById(R.id.web_view);
        urlEditText = findViewById(R.id.urlEditText);
        //Set the webView required settings
        webView.getSettings().setJavaScriptEnabled(true);
        //Loads the default Cache Policy
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.canGoBack();
        //Customize WebViewClient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                HashMap<String, String> headerExtras = new HashMap<>();
                headerExtras.put("ANDROID_HEADER_EXTRA", "HELLO_WORLD");
                view.loadUrl(url, headerExtras);
                //When a new url is loaded, checks if the url matches the the preferred url.
                if (url.contains("#test")) {
                    Toast.makeText(getApplicationContext(),requestHeaders,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), ConnectionErrorActivity.class).putExtra("header",requestHeaders));
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                requestHeaders = request.getRequestHeaders().toString();
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("#test")) {
                    startActivity(new Intent(getApplicationContext(), ConnectionErrorActivity.class).putExtra("header",requestHeaders));
                }
                super.onPageFinished(view, url);
            }
        });
        urlEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) urlEditText.setText(getResources().getString(R.string.https));
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
        Toast.makeText(this,"Press Back again to exit!",Toast.LENGTH_LONG).show();
        webView.goBack();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickedOnce = false;
            }
        },2000);
    }
}
