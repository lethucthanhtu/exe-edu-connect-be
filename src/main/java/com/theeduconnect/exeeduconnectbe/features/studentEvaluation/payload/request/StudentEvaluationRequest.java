package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEvaluationRequest {
    @Schema(name = "isPresent", example = "true")
    @NotNull(message = "isPresent is required") private Boolean isPresent;

    @Schema(name = "comment", example = "Good performance")
    @NotBlank(message = "comment is required")
    private String comment;

    @Schema(name = "courseScheduleId", example = "1")
    @NotNull(message = "courseScheduleId is required") private Integer courseScheduleId;
}
