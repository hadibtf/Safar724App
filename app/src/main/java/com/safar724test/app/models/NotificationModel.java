package com.safar724test.app.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class NotificationModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "notifId")
    private String notifId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "icon")
    private String icon;
    @ColumnInfo(name = "actions")
    private NotifActions notifActions;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "expires")
    private String expires;
    @ColumnInfo(name = "tags")
    private List<NotifTag> tags;
    @ColumnInfo(name = "conditions")
    private List<Condition> conditions;
    @ColumnInfo(name = "ver")
    private String ver;
    @ColumnInfo(name = "type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public NotifActions getNotifActions() {
        return notifActions;
    }

    public void setNotifActions(NotifActions notifActions) {
        this.notifActions = notifActions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public List<NotifTag> getTags() {
        return tags;
    }

    public void setTags(List<NotifTag> tags) {
        this.tags = tags;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
