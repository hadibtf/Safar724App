package com.safar724test.app.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.safar724test.app.models.NotificationModel;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface NotificationDataDao {
    @Query("Select * from NotificationModel")
    Flowable<List<NotificationModel>> getNotificationModelList();

    @Insert
    void insertNotificationModel(NotificationModel notificationModel);

    @Update
    void updateNotificationModel(NotificationModel notificationModel);

    @Delete
    void deleteNotificationData(NotificationModel notificationModel);
}
