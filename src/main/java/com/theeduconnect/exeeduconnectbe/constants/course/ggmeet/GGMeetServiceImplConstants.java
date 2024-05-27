package com.theeduconnect.exeeduconnectbe.constants.course.ggmeet;

import com.google.api.services.calendar.CalendarScopes;
import java.util.Collections;
import java.util.List;

public class GGMeetServiceImplConstants {

    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static final String APPLICATION_NAME = "ExeEduConnectBeApplicationTests";
    public static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    public static final String CREDENTIALS_FILE_PATH = "/googleMeetCredentials.json";
    public static final String TIMEZONE = "Asia/Ho_Chi_Minh";
    public static final String[] RECURRENCE_SETTING = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
}
