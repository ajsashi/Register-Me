package com.register.me.model.data.util;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DateTimeUtil {

    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PRETTY_PATTERN = "dd.MM.yyyy HH:mm";
    public static final String TITLE_PATTERN = "dd MMM yyyy";
    public static final String REQUEST_DATE_FORMAT = "dd-MM-yyyy";

    public static String getRequestDateFormatted(DateTime date) {
        return date.toString(REQUEST_DATE_FORMAT);
    }

    public static String toString(DateTime dateTime) {
        return dateTime.toString(PATTERN);
    }

    public static DateTime toDateTime(String dateTime) {
        return DateTime.parse(dateTime, DateTimeFormat.forPattern(PATTERN));
    }

    public static String toPrettyString(DateTime time) {
        return time.toString(PRETTY_PATTERN);
    }

    public static String toTitleString(DateTime dateTime) {
        return dateTime.toString(TITLE_PATTERN);
    }

    public static DateTime toDateTimeFromMillis(long millis) {
        return new DateTime(millis);
    }

    public void convertUTCtoLocal() throws ParseException {
        /*long ts = System.currentTimeMillis();
        Date localTime = new Date(ts);*/
        String da ="2020-02-26T13:42:51";
        String format = "yyyy-MM-ddTHH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date lt = sdf.parse(da);
        // Convert Local Time to UTC (Works Fine)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmtTime = new Date(sdf.format(da));
        /*System.out.println("Local:" + localTime.toString() + "," + localTime.getTime() + " --> UTC time:"
                + gmtTime.toString() + "," + gmtTime.getTime());*/

        // **** YOUR CODE **** END ****
        // Convert UTC to Local Time
        Date fromGmt = new Date(lt.getTime() + TimeZone.getDefault().getOffset(lt.getTime()));
        Log.d("UTC time:", lt.toString() + "," + lt.getTime() + " --> Local:"
                + fromGmt.toString() + "-" + fromGmt.getTime());
    }
}
