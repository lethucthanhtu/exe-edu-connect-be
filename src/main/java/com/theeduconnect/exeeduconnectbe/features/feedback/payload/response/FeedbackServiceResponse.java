package com.theeduconnect.exeeduconnectbe.features.feedback.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedbackServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
