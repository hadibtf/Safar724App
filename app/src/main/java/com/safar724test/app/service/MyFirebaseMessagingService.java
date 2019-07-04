package com.safar724test.app.service;


import com.google.firebase.messaging.FirebaseMessagingService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.widget.RemoteViews;

import com.google.firebase.messaging.RemoteMessage;
import com.safar724test.app.R;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.interfaces.NotificationDataDao;
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

        NotificationDataDao dao = NotificationDataDatabase.getInstance(getApplicationContext()).notificationDataDao();

        Map<String, String> params = message.getData();
        JSONObject object = new JSONObject(params);
        System.out.println("====" + object.toString());

        final Notification notifData = new Notification(
                params.get("key1"),
                params.get("key2"),
                params.get("notifIcon"),
                params.get("notif_data"),
                params.get("body"),
                params.get("title")
        );
        try {
            JSONArray array = new JSONArray(object.getString("notif_data"));
            System.out.println("===="+array.getJSONObject(0).getString("description"));
            for (int i = 0; i <= array.length(); i++) {
                NotificationData data = new NotificationData(
                        array.getJSONObject(i).getString("description"),
                        array.getJSONObject(i).getString("notif_icon"),
                        array.getJSONObject(i).getString("url")
                );
                dao.insertNotificationData(data);
            }
        } catch (JSONException e) {
            System.out.println("===="+e);
            e.printStackTrace();
        }
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