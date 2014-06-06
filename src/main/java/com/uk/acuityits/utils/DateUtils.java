package com.uk.acuityits.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_TIME_WITHOUT_SECOND_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat HOUR_MINUTE_FORMAT = new SimpleDateFormat(
            "HHmm");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final SimpleDateFormat HOUR_MINUTE_SEPRATOR_FORMAT = new SimpleDateFormat(
            "HH:mm");

    private DateUtils() {
        
    }
    
    public static String convertRequestDateToString(Date date) {
        return DATE_TIME_FORMAT.format(date);
        
    }

    public static Date convertStringToRequestDate(String dateString)
            throws ParseException {
        return DATE_TIME_FORMAT.parse(dateString);
        
    }

    public static String convertBookingDateToString(Date date) {
        return DATE_FORMAT.format(date);
        
    }

    public static Date convertStringToBookingDate(String dateString)
            throws ParseException {
        return DATE_TIME_WITHOUT_SECOND_FORMAT.parse(dateString);
        
    }

    public static Date convertStringToWorkingHour(String hourString)
            throws ParseException {
        return HOUR_MINUTE_FORMAT.parse(hourString);
    }

    public static String convertDateToWorkingHourString(Date date)
            throws ParseException {
        return HOUR_MINUTE_SEPRATOR_FORMAT.format(date);
    }

    public static Date convertDateWithoutHours(Date date) {
        String dateString = convertBookingDateToString(date);
        Date newDate = null;
        try {
            newDate = DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
