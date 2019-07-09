package com.safar724test.app.service;


import com.google.firebase.messaging.FirebaseMessagingService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.RemoteMessage;
import com.safar724test.app.R;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.interfaces.NotificationDataDao;
import com.safar724test.app.models.NotificationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "FMService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        System.out.println("====" + message);

        NotificationDataDao dao = NotificationDataDatabase.getInstance(getApplicationContext()).notificationDataDao();
        Map<String, String> notif = message.getData();
//        JSONObject object = new JSONObject(notif);
        NotificationData data = new NotificationData(
                notif.get("description"),
                notif.get("notif_icon"),
                notif.get("date"),
                notif.get("url")
        );
        dao.insertNotificationData(data);

//        try {
//            JSONArray array = new JSONArray(object.getString("notif_data"));
//            for (int i = 0; i <= array.length(); i++) {
//                NotificationData data = new NotificationData(
//                        notif.get("description"),
//                        notif.get("notif_icon"),
//                        notif.get("url")
//                );
//                dao.insertNotificationData(data);
//            }
//        } catch (JSONException e) {
//            Log.d(TAG, e.toString());
//            e.printStackTrace();
//        }
        sendNotification(notif);
        super.onMessageReceived(message);
    }

    private void sendNotification(Map<String, String> notifData) {
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
        big.setTextViewText(R.id.expanded_notif_title, notifData.get("title"));
        big.setTextViewText(R.id.expanded_notif_description, notifData.get("body"));
        small.setTextViewText(R.id.collapsed_notif_title, notifData.get("title"));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getResources().getString(R.string.CHANNEL_ID))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(small)
                .setCustomBigContentView(big)
                .setSmallIcon(R.mipmap.ic_launcher);
        notificationManagerCompat.notify(1, builder.build());
    }
}