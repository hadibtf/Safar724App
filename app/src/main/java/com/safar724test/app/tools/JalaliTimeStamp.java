package com.safar724test.app.tools;


import java.util.GregorianCalendar;

import ir.huri.jcal.JalaliCalendar;

public class JalaliTimeStamp extends JalaliCalendar {
    private final String date;

    public JalaliTimeStamp(String date) {
        this.date = date;
    }

    public String getDate() {
        String[] dateString = date.split("-");
        JalaliCalendar jcl = new JalaliCalendar(new GregorianCalendar(Integer.valueOf(dateString[0]), Integer.valueOf(dateString[1]) - 1, Integer.valueOf(dateString[2])));
        return jcl.getYear() + "/" + jcl.getMonth() + "/" + jcl.getDay();
    }

    public String getDateInPersian() {
        String[] dateString = date.split("-");
        JalaliCalendar jcl = new JalaliCalendar(new GregorianCalendar(Integer.valueOf(dateString[0]), Integer.valueOf(dateString[1]) - 1, Integer.valueOf(dateString[2])));
        return faToEn(jcl.getYear() + "/" + jcl.getMonth() + "/" + jcl.getDay());
    }

    private String faToEn(String num) {
        return num
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹");
    }
}
