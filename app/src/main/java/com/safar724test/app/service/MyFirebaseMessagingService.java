package com.safar724test.app.service;


import com.google.firebase.messaging.FirebaseMessagingService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.firebase.messaging.RemoteMessage;
import com.safar724test.app.R;
import com.safar724test.app.models.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        System.out.println("====" + message);


        Map<String, String> params = message.getData();
        JSONObject object = new JSONObject(params);
        final Data notifData = new Data(
                params.get("key1"),
                params.get("key2"),
                params.get("notifIcon"),
                params.get("body"),
                params.get("title")
        );
        SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {
            editor.putString("notif_data",object.getString("notif_data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.commit();
        sendNotification(notifData);
        super.onMessageReceived(message);
    }

    private void sendNotification(Data notifData) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelId = getResources().getString(R.string.CHANNEL_ID);
            CharSequence channelName = "Test Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        RemoteViews small = new RemoteViews(getPackageName(), R.layout.custom_notification_collapsed);
        RemoteViews big = new RemoteViews(getPackageName(), R.layout.custom_notification_expanded);
        big.setTextViewText(R.id.expanded_notif_title, notifData.getTitle());
        big.setTextViewText(R.id.expanded_notif_info, notifData.getBody());
        small.setTextViewText(R.id.collapsed_notif_title, notifData.getTitle());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getResources().getString(R.string.CHANNEL_ID))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(small)
                .setCustomBigContentView(big);
        notificationManagerCompat.notify(1, builder.build());
    }
}