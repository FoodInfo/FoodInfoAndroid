package com.xpn.foodinfo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class Time {

    public static String getDateInIso(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        df.setTimeZone(tz);
        return df.format(date);
    }
}
