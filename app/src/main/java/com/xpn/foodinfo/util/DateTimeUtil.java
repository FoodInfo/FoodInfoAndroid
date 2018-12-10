package com.xpn.foodinfo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateTimeUtil {

    private static final DateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'", Locale.US);


    public static String dateToISO(Date date) {
        return ISO_8601_FORMAT.format(date);
    }

    public static Date ISOToDate(String isoDate) throws ParseException {
        return ISO_8601_FORMAT.parse(isoDate);
    }
}
