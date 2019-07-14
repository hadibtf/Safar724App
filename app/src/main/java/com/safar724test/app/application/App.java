package com.safar724test.app.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String fcmToken = sharedPref.getString("fcmToken", "");
        if (fcmToken.equals("")) {
            FirebaseMessaging.getInstance().subscribeToTopic("safar724").addOnCompleteListener(task ->
                    Toast.makeText(getApplicationContext(), "topic created!", Toast.LENGTH_LONG).show());
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.d("App", "Failed to get FCM token");
                    return;
                }
                String token = task.getResult().getToken();
                System.out.println("----token: " + token);
                sharedPref.edit().putString("fcmToken", token).apply();
            });
        }
    }
}
