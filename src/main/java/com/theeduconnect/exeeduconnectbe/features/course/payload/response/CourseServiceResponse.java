package com.theeduconnect.exeeduconnectbe.features.course.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
