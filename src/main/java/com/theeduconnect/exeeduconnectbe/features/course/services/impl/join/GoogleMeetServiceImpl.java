package com.theeduconnect.exeeduconnectbe.features.course.services.impl.join;

import static com.theeduconnect.exeeduconnectbe.constants.course.GGMeetServiceImplConstants.*;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.theeduconnect.exeeduconnectbe.constants.course.GGMeetServiceImplConstants;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.NewGoogleMeetUrlDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class GoogleMeetServiceImpl {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public String GetCalendarUrl(NewGoogleMeetUrlDto request)
            throws IOException, GeneralSecurityException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        Event event =
                new Event()
                        .setSummary(request.getSummary())
                        .setDescription(request.getDescription());

        EventDateTime start =
                new EventDateTime()
                        .setDateTime(request.getStartDateTime())
                        .setTimeZone("Asia/Ho_Chi_Minh");
        event.setStart(start);

        EventDateTime end =
                new EventDateTime().setDateTime(request.getEndDateTime()).setTimeZone(TIMEZONE);
        event.setEnd(end);

        event.setRecurrence(Arrays.asList(RECURRENCE_SETTING));

        EventAttendee[] attendees =
                new EventAttendee[] {
                    new EventAttendee().setEmail(request.getTeacherEmail()),
                };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides =
                new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
                };
        Event.Reminders reminders =
                new Event.Reminders()
                        .setUseDefault(false)
                        .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        return event.getHtmlLink();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        InputStream in = GoogleMeetServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(
                                new FileDataStoreFactory(
                                        new java.io.File(
                                                GGMeetServiceImplConstants.TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline")
                        .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;
    }
}
