package com.safar724test.app.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.safar724test.app.models.NotificationData;

import java.util.List;

import io.reactivex.Flowable;



@Dao
public interface NotificationDataDao {
    @Query("Select * from NotificationData")
    Flowable<List<NotificationData>> getNotificationDataList();

    @Insert
    void insertNotificationData(NotificationData notificationData);

    @Update
    void updateNotificationData(NotificationData notificationData);

    @Delete
    void deleteNotificationData(NotificationData notificationData);
}
