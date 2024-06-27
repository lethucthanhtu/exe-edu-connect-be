package com.theeduconnect.exeeduconnectbe.features.course.dtos;

import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewGoogleMeetUrlDto {
    private String summary;
    private String description;
    private DateTime startdatetime;
    private DateTime enddatetime;
    private String teacheremail;
    private String studentemail;
}
