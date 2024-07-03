package com.theeduconnect.exeeduconnectbe.utils;

import com.google.api.client.util.DateTime;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
    public static LocalDate StringToLocalDate(String input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, formatter);
    }
}
