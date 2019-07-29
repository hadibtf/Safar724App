package com.safar724test.app.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.safar724test.app.R;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.interfaces.NotificationDataDao;
import com.safar724test.app.models.NotifActions;
import com.safar724test.app.models.NotifCondition;
import com.safar724test.app.models.NotifTag;
import com.safar724test.app.models.NotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "FMService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        JSONObject data = new JSONObject(message.getData());
        NotificationModel model = new NotificationModel();
        try {
            model.setNotifId(data.getString("notif_id"));
            model.setTitle(data.getString("title"));
            model.setDescription(data.getString("description"));
            model.setIcon(data.getString("icon"));

            JSONArray jsonArrayActions = new JSONArray(data.getString("actions"));
            List<NotifActions> notifActions = new ArrayList<>();
            for (int i = 0; i < jsonArrayActions.length(); i++) {
                JSONObject json = jsonArrayActions.getJSONObject(i);
                String actionType = json.getString("type");
                String actionData = json.getString("data");
                NotifActions action = new NotifActions(actionType, actionData);
                notifActions.add(action);
            }
            model.setNotifActions(notifActions);

            model.setDate(data.getString("date"));
            model.setExpires(data.getString("expires"));

            JSONArray jsonArrayTags = new JSONArray(data.getString("tags"));
            List<NotifTag> notifTags = new ArrayList<>();
            for (int i = 0; i < jsonArrayTags.length(); i++) {
                JSONObject json = jsonArrayTags.getJSONObject(i);
                String name = json.getString("name");
                String title = json.getString("title");
                String color = json.getString("color");
                String icon = json.getString("icon");
                NotifTag notifTag = new NotifTag(name, title, color, icon);
                notifTags.add(notifTag);
            }
            model.setTags(notifTags);

            JSONArray conditionsJsonArray = new JSONArray(data.getString("conditions"));
            List<NotifCondition> notifConditions = new ArrayList<>();
            for (int i = 0; i < conditionsJsonArray.length(); i++) {
                JSONObject json = conditionsJsonArray.getJSONObject(i);
                String type = json.getString("appversion");
                String lessThan = json.getString("lessThan");
                String to = json.getString("to");
                String moreThan = json.getString("moreThan");
                NotifCondition notifCondition = new NotifCondition(type, lessThan, to, moreThan);
                notifConditions.add(notifCondition);
            }
            model.setNotifConditions(notifConditions);

            model.setVer(data.getString("ver"));
            model.setType(data.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NotificationDataDao dao = NotificationDataDatabase.getInstance(getApplicationContext()).notificationDataDao();
        dao.insertNotificationData(model);
        sendNotification(message.getData());
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
        big.setTextViewText(R.id.expanded_notif_description, notifData.get("description"));
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