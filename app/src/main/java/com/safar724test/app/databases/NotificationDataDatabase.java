package com.safar724test.app.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.safar724test.app.interfaces.NotificationDataDao;
import com.safar724test.app.models.NotificationModel;

@Database(entities = NotificationModel.class, exportSchema = false, version = 7)
public abstract class NotificationDataDatabase extends RoomDatabase {
    private static final String DB_NAME = "notification_db";
    private static NotificationDataDatabase instance;

    public static synchronized NotificationDataDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NotificationDataDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract NotificationDataDao notificationDataDao();
}
