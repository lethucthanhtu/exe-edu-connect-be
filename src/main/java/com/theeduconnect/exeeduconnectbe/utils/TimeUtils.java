package com.theeduconnect.exeeduconnectbe.utils;

import com.google.api.client.util.DateTime;
import java.time.*;
import java.time.format.DateTimeFormatter;

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
    //    public static Instant TimeStringToInstant(String input){
    //        LocalTime localTime = LocalTime.parse(input);
    //        // Combine the date and time to get a LocalDateTime
    //        LocalDateTime dateTime = LocalDateTime.of(currentDate, localTime);
    //
    //        // Convert LocalDateTime to ZonedDateTime with UTC zone
    //        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
    //
    //        // Convert ZonedDateTime to Instant
    //        return zonedDateTime.toInstant();
    //    }
}
