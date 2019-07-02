package com.safar724test.app.models;

public class Notification {
    final public String key1;
    final public String key2;
    final public String notifIcon;
    final public String body;
    final public String title;

    public Notification(String key1, String key2, String notifIcon, String body, String title) {
        this.key1 = key1;
        this.key2 = key2;
        this.notifIcon = notifIcon;
        this.body = body;
        this.title = title;
    }
}
