package com.safar724test.app.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class NotificationData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "description")
    final public String description;
    @ColumnInfo(name = "iconUrl")
    final public String iconUrl;
    @ColumnInfo(name = "url")
    final public String url;
//    @ColumnInfo(name = "expirationDate")
//    final public String expirationDate = null;


    public NotificationData(String description, String iconUrl, String url) {
        this.description = description;
        this.iconUrl = iconUrl;
        this.url = url;
    }
}
