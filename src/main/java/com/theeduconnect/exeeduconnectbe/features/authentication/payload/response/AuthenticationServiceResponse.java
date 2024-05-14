package com.theeduconnect.exeeduconnectbe.features.authentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;

}
