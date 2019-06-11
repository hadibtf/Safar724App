package com.safar724.app.application;

import android.app.Application;

public class App extends Application {
    private String recentUrl;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setRecentUrl(String recentUrl) {
        this.recentUrl = recentUrl;
    }


    public String getRecentUrl() {
        return recentUrl;
    }
}
