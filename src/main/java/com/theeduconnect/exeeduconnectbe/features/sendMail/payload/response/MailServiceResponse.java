package com.theeduconnect.exeeduconnectbe.features.sendMail.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
