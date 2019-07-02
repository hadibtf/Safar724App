package com.safar724test.app.models;

import java.util.Date;

public class NotificationData {
    final public String notifDescription;
    final public String notifIconUrl;
    final public Date expirationDate = null;


    public NotificationData(String notifDescription, String notifIconUrl) {
        this.notifDescription = notifDescription;
        this.notifIconUrl = notifIconUrl;
    }

}
