package com.theeduconnect.exeeduconnectbe.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class TimeUtils {
    public static boolean IsInstantBetweenLocalDates(
            Instant instant, LocalDate startDate, LocalDate endDate) {
        Instant startInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endInstant = endDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        if (instant.isAfter(endInstant) || instant.isBefore(startInstant)) return false;
        return true;
    }
}
