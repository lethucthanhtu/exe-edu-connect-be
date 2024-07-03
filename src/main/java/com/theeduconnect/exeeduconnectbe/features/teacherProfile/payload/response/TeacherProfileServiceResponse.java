package com.theeduconnect.exeeduconnectbe.features.teacherProfile.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeacherProfileServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
