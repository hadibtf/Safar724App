package com.safar724test.app.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "notificationdata")
public class NotificationData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "description")
    final public String description;
    @ColumnInfo(name = "iconUrl")
    final public String iconUrl;
    @ColumnInfo(name = "url")
    final public String url;
//    @ColumnInfo(name = "expirationDate")
//    final public String expirationDate = null;


    public NotificationData(int id,String description, String iconUrl, String url) {
        this.id = id;
        this.description = description;
        this.iconUrl = iconUrl;
        this.url = url;
    }

    public int getId() {
        return id;
    }
}
