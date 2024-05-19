package com.theeduconnect.exeeduconnectbe.features.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
