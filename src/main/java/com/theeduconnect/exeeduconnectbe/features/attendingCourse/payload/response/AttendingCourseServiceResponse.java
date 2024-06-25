package com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttendingCourseServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
