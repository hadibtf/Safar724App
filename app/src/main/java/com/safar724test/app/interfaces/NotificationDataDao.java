package com.safar724test.app.interfaces;

import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.safar724test.app.models.NotificationData;

import java.util.List;

@Dao
public interface NotificationDataDao {
    @Query("Select * from notificationdata")
    List<NotificationData> getNotificationDataList();

    @Insert
    void insertNotificationData(NotificationData notificationData);

    @Update
    void updateNotificationData(NotificationData notificationData);

    @Update
    void deleteNotificationData(NotificationData notificationData);
}
