package com.theeduconnect.exeeduconnectbe.utils;

import com.google.api.client.util.DateTime;
import java.time.*;

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
}
