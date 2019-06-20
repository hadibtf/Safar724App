package com.safar724test.app.objects;

public class Data {
    final private String key2;
    final private String key1;
    final private String notifIcon;
    final private String body;
    final private String title;

    public Data(String key1, String key2, String notifIcon, String body, String title) {
        this.key1 = key1;
        this.key2 = key2;
        this.notifIcon = notifIcon;
        this.body = body;
        this.title = title;
    }

    public String getKey2() {
        return key2;
    }

    public String getKey1() {
        return key1;
    }

    public String getNotifIcon() {
        return notifIcon;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }
}
