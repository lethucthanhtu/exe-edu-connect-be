package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEvaluationRequest {
    private Boolean isPresent;
    private String comment;
    private Integer courseScheduleId;
}

