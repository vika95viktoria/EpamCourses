package com.epam.lowcost.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Виктория on 26.02.2016.
 */
public class DateUtils {
    private static Locale locale = new Locale("en", "US");
    private static long DAY = 86400000;
    private static long WEEK = 604800000;

    public static String formateDate(Date date, String locale) {
        Locale locale1 = new Locale(locale.substring(0, 2), locale.substring(3, 5));
        Locale.setDefault(locale1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
        return format(date, dateFormat);
    }

    public static String formateDateForCabinet(Date date, String locale) {
        Locale locale1 = new Locale(locale.substring(0, 2), locale.substring(3, 5));
        Locale.setDefault(locale1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MMM d");
        return format(date, dateFormat);
    }

    private static String format(Date date, SimpleDateFormat dateFormat) {
        Locale.setDefault(locale);
        String formDate = dateFormat.format(date);
        return formDate;
    }

    public static Date addWeek(Date startDate) {
        Long time = startDate.getTime() + WEEK;
        Date date = new Date(time);
        return date;
    }

    public static Date addDays(Date startDate, int countOfDays) {
        Long time = startDate.getTime() + countOfDays * DAY;
        Date date = new Date(time);
        return date;
    }

    public static Date checkDates(Date departure, Date arrival) {
        if (arrival.before(departure)) {
            arrival = addDays(arrival, 1);
        }
        return arrival;
    }

    public static Date parseDate(String dateInString, String time) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a", Locale.getDefault());
        Calendar calendar = GregorianCalendar.getInstance();
        Date date = new Date();
        Date timeForDate = new Date();
        try {
            date = dateFormatter.parse(dateInString);
            timeForDate = timeFormatter.parse(time);
            calendar.setTime(timeForDate);
            int hour = calendar.get(Calendar.HOUR);
            int minutes = calendar.get(Calendar.MINUTE);
            calendar.setTime(date);
            if (time.substring(time.length() - 2, time.length()).equals("PM")) {
                hour += 12;
            }
            calendar.add(Calendar.HOUR, hour);
            calendar.add(Calendar.MINUTE, minutes);
            calendar.getTime();
        } catch (ParseException e) {
            System.err.println(e);
        }
        return calendar.getTime();
    }

    public static Date parseDate(Date date, String time) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a", Locale.getDefault());
        Calendar calendar = GregorianCalendar.getInstance();
        Date timeForDate = new Date();
        try {
            timeForDate = timeFormatter.parse(time);
            calendar.setTime(timeForDate);
            int hour = calendar.get(Calendar.HOUR);
            int minutes = calendar.get(Calendar.MINUTE);
            calendar.setTime(date);
            if (time.substring(time.length() - 2, time.length()).equals("PM")) {
                hour += 12;
            }
            calendar.add(Calendar.HOUR, hour);
            calendar.add(Calendar.MINUTE, minutes);
            calendar.getTime();
        } catch (ParseException e) {
            System.err.println(e);
        }
        return calendar.getTime();
    }

    public static Date getDateWithoutTime(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
