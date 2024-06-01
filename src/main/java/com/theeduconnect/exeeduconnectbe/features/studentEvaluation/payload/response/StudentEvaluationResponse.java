package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentEvaluationResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
