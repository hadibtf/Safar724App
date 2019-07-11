package com.safar724test.app;

import com.safar724test.app.activities.WebViewActivity;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebViewActivityTest {

    @Test
    public void onCreate() {
        WebViewActivity webViewActivity = new WebViewActivity();
        assertNotNull(webViewActivity);
    }

    @Test
    public void goToUrl() {
    }

    @Test
    public void onBackPressed() {
    }
}