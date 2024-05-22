package com.theeduconnect.exeeduconnectbe.features.teacher.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeacherServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
