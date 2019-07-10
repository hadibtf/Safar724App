package com.safar724test.app.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NotificationData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "iconUrl")
    private String iconUrl;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "isRead")
    private boolean isRead = false;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public boolean isRead() {
        return isRead;
    }
}
