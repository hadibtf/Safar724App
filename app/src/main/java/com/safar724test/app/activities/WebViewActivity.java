package com.safar724test.app.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WebViewActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        myWebView.loadUrl("https://mob.safar724.com");
        myWebView.getSettings().setJavaScriptEnabled(true);
//        Date date = new Date("2019,6,26");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
//        calendar.set();

        System.out.println("=====>" + simpleDateFormat.format(calendar.getTime()));
        Toast.makeText(this, simpleDateFormat.format(calendar.getTime()), Toast.LENGTH_LONG).show();
    }
}
