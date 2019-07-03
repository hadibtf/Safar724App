package com.safar724test.app.service;


import com.google.firebase.messaging.FirebaseMessagingService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.safar724test.app.R;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.models.Notification;
import com.safar724test.app.models.NotificationData;

import org.json.JSONArray;
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

        NotificationDataDatabase database = NotificationDataDatabase.getInstance(getApplicationContext());

        Map<String, String> params = message.getData();
        JSONObject object = new JSONObject(params);
        final Notification notifData = new Notification(
                params.get("key1"),
                params.get("key2"),
                params.get("notifIcon"),
                params.get("notif_data"),
                params.get("body"),
                params.get("title")
        );
//        SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
        try {
//            editor.putString("notif_data",object.getString("notif_data"));
//            NotificationData data = new NotificationData();
            JSONArray array = new JSONArray(object.getString("notif_data"));
            for (int i = 0; i <= array.length(); i++) {
                NotificationData data = new NotificationData(
                        i,
                        array.getJSONObject(i).getString("body"),
                        array.getJSONObject(i).getString("notif_icon"),
                        array.getJSONObject(i).getString("url")
                );
                Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
                database.notificationDataDao().insertNotificationData(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        editor.commit();
        sendNotification(notifData);
        super.onMessageReceived(message);
    }

    private void sendNotification(Notification notifData) {
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
        big.setTextViewText(R.id.expanded_notif_title, notifData.title);
        big.setTextViewText(R.id.expanded_notif_info, notifData.body);
        small.setTextViewText(R.id.collapsed_notif_title, notifData.title);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getResources().getString(R.string.CHANNEL_ID))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(small)
                .setCustomBigContentView(big);
        notificationManagerCompat.notify(1, builder.build());
    }
}