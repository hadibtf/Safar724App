package com.safar724test.app.models;

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
}
