package com.theeduconnect.exeeduconnectbe.utils;

import com.google.api.client.util.DateTime;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TimeUtils {
    public static boolean IsInstantBetweenLocalDates(
            Instant instant, LocalDate startDate, LocalDate endDate) {
        Instant startInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endInstant = endDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        if (instant.isAfter(endInstant) || instant.isBefore(startInstant)) return false;
        return true;
    }

    public static DateTime InstantToGoogleDateTime(Instant instant) {
        java.util.Date date = java.util.Date.from(instant);
        return new DateTime(date);
    }

    public static LocalDate StringToLocalDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, formatter);
    }

    public static String InstantToTimeString(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        int hour = zonedDateTime.getHour();
        int minute = zonedDateTime.getMinute();
        int second = zonedDateTime.getSecond();
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static String InstantToLocalDateString(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return zonedDateTime.toLocalDate().toString();
    }

    public static String GetDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "SUNDAY";
            case Calendar.MONDAY:
                return "MONDAY";
            case Calendar.TUESDAY:
                return "TUESDAY";
            case Calendar.WEDNESDAY:
                return "WEDNESDAY";
            case Calendar.THURSDAY:
                return "THURSDAY";
            case Calendar.FRIDAY:
                return "FRIDAY";
            case Calendar.SATURDAY:
                return "SATURDAY";
            default:
                return "INVALID DAY";
        }
    }
}
