package com.safar724test.app.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class NotifActions {
    public final String type;
    public final String data;

    public NotifActions(String type, String data) {
        this.type = type;
        this.data = data;
    }

    @TypeConverter
    public static List<NotifActions> stringToNotifActionsList(String data) {
        if (data == null) return Collections.emptyList();

        Type listType = new TypeToken<List<NotifActions>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String notifActionsListToString(List<NotifActions> notifActions) {
        Gson gson = new Gson();
        return gson.toJson(notifActions);
    }
}
