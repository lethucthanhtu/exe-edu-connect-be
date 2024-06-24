package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEvaluationDto {
    private Integer id;
    private Boolean ispresent;
    private String comment;
    private Integer coursescheduleid;
}
