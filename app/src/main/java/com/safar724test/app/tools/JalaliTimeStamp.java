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
}
