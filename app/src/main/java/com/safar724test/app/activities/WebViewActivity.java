package com.safar724test.app.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WebViewActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        myWebView.loadUrl("https://mob.safar724.com");
        myWebView.getSettings().setJavaScriptEnabled(true);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd");

        Calendar today = Calendar.getInstance();
        Calendar expirationDate = Calendar.getInstance();
        String xDate = "2019/12/25";
        String[] stringDateItems = xDate.split("/");
        ArrayList<Integer> intDateItems = new ArrayList<>();
        for (String dateItem : stringDateItems) {
            intDateItems.add(Integer.parseInt(dateItem));
        }
        expirationDate.set(intDateItems.get(0), intDateItems.get(1), intDateItems.get(2));

        System.out.println("=====>now" + simpleDateFormat.format(today.getTime()));
        System.out.println("=====>notif" + simpleDateFormat.format(expirationDate.getTime()));
        if (expirationDate.before(today)) {
            System.out.println("========expired");
        }
        Toast.makeText(this, simpleDateFormat.format(today.getTime()), Toast.LENGTH_LONG).show();
    }
}
