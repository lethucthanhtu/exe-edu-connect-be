package com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseScheduleResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
