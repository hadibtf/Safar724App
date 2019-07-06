package com.safar724test.app.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.safar724test.app.R;

public class App extends Application {
    private String recentUrl;
    private static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = this;
        FirebaseMessaging.getInstance().subscribeToTopic("safar724").addOnCompleteListener(task -> Toast.makeText(getApplicationContext(), "topic created!", Toast.LENGTH_LONG).show());
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                System.out.println("----+: " + "failed");
                return;
            }
            // Get new Instance ID token
            String token = task.getResult().getToken();
            System.out.println("----token: " + token);
        });
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
