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
import com.safar724test.app.models.NotificationData;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "FMService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> notif = message.getData();

        NotificationData data = new NotificationData();
        data.setTitle(notif.get("title"));
        data.setDescription(notif.get("description"));
        data.setIconUrl(notif.get("notif_icon"));
        data.setDate(notif.get("date"));
        data.setUrl(notif.get("url"));

        NotificationDataDao dao = NotificationDataDatabase.getInstance(getApplicationContext()).notificationDataDao();
        dao.insertNotificationData(data);

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