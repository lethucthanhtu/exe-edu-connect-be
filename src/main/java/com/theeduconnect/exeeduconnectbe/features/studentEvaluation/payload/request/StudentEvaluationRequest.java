package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEvaluationRequest {
    @Schema(name = "ispresent", example = "true")
    @NotNull(message = "ispresent is required") private Boolean ispresent;

    @Schema(name = "comment", example = "Good performance")
    @NotBlank(message = "comment is required")
    private String comment;

    @Schema(name = "coursescheduleid", example = "1")
    @NotNull(message = "coursescheduleid is required") private Integer coursescheduleid;
}
