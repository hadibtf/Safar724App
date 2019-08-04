package com.safar724test.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import timber.log.Timber;

public class G extends Application {
    private String notifToken;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String fcmToken = sharedPref.getString("fcmToken", "");

        if (fcmToken.equals("")) {
            FirebaseMessaging
                    .getInstance()
                    .subscribeToTopic("safar724")
                    .addOnCompleteListener(
                            task -> Timber.d("G.java: Subscription to Safar724 channel was Successful!"));

            FirebaseInstanceId
                    .getInstance()
                    .getInstanceId()
                    .addOnCompleteListener(
                            task -> {
                        if (!task.isSuccessful()) {
                            Timber.d("Failed to get FCM token");
                            return;
                        }
                        notifToken = task.getResult().getToken();
                        Timber.d("----token: %s", notifToken);
                        sharedPref.edit().putString("fcmToken", notifToken).apply();
                    }
            );
        }
    }

    public String getNotifToken() {
        return notifToken;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

