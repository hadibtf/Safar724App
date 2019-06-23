package com.safar724test.app.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.safar724test.app.R;

public class App extends Application {
    private String recentUrl;
    private static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = this;
    }

//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

    public void setRecentUrl(String recentUrl) {
        this.recentUrl = recentUrl;
    }


    public String getRecentUrl() {
        return recentUrl;
    }
}
