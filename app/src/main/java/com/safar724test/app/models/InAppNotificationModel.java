package com.safar724test.app.models;

import java.util.Date;

public class InAppNotificationModel {
    final private String notifDescription;
    final private String notifIconUrl;
    final private Date expirationDate = null;


    public InAppNotificationModel(String notifDescription, String notifIconUrl) {
        this.notifDescription = notifDescription;
        this.notifIconUrl = notifIconUrl;
    }

    public String getNotifDescription() {
        return notifDescription;
    }

    public String getNotifIconUrl() {
        return notifIconUrl;
    }
}
