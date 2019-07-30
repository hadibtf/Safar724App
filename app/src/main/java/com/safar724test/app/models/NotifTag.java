package com.safar724test.app.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class NotifTag {
    public final String name;
    public final String title;
    public final String color;
    public final String icon;

    public NotifTag(String name, String title, String color, String icon) {
        this.name = name;
        this.title = title;
        this.color = color;
        this.icon = icon;
    }

    @TypeConverter
    public static List<NotifTag> stringToNotifTag(String data) {
        if (data == null) Collections.emptyList();
        Type listType = new TypeToken<List<NotifTag>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String notifTag(List<NotifTag> notifTags){
        Gson gson = new Gson();
        return gson.toJson(notifTags);
    }
}
