package com.theeduconnect.exeeduconnectbe.utils;

import java.util.UUID;

public class JitsiMeetUtils {
    public static String generateMeetUrl() {
        String meetingId = UUID.randomUUID().toString();
        return "https://meet.jit.si/" + meetingId;
    }
}
