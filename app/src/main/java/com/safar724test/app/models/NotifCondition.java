package com.safar724test.app.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class NotifCondition {
    private final String type;
    private final String lessThan;
    private final String to;
    private final String moreThan;

    public NotifCondition(String type, String lessThan, String to, String moreThan) {
        this.type = type;
        this.lessThan = lessThan;
        this.to = to;
        this.moreThan = moreThan;
    }

    @TypeConverter
    public static List<NotifCondition> stringToNotifCondition(String data) {
        if (data == null) return Collections.emptyList();
        Type listType = new TypeToken<List<NotifCondition>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String notifCondition(List<NotifCondition> notifConditions){
        Gson gson = new Gson();
        return gson.toJson(notifConditions);
    }


}
