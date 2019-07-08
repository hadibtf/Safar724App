package com.safar724test.app.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NotificationData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "description")
    final public String description;
    @ColumnInfo(name = "iconUrl")
    final public String iconUrl;
    @ColumnInfo(name = "date")
    final public String date;
    @ColumnInfo(name = "url")
    final public String url;

    public NotificationData(String description, String iconUrl, String date, String url) {
        this.description = description;
        this.iconUrl = iconUrl;
        this.date = date;
        this.url = url;
    }
}
