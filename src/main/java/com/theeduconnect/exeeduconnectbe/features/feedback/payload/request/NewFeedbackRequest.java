package com.theeduconnect.exeeduconnectbe.features.feedback.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theeduconnect.exeeduconnectbe.constants.feedback.validation.FeedbackValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.feedback.validation.FeedbackValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewFeedbackRequest {
    @Schema(name = "star", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(
            value = FeedbackValidationSpecifications.MIN_STAR,
            message = FeedbackValidationMessages.INVALID_STAR)
    @Max(
            value = FeedbackValidationSpecifications.MAX_STAR,
            message = FeedbackValidationMessages.INVALID_STAR)
    private Integer star;

    @Schema(
            name = "content",
            example =
                    "The course is highly suitable for those seeking to improve their Mathematics"
                            + " performance!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(
            min = FeedbackValidationSpecifications.MIN_DESCRIPTION_LENGTH,
            max = FeedbackValidationSpecifications.MAX_DESCRIPTION_LENGTH,
            message = FeedbackValidationMessages.INVALID_DESCRIPTION)
    private String content;

    @JsonIgnore private Integer studentid;

    @Schema(name = "attendingcourseid", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = FeedbackValidationMessages.INVALID_ATTENDING_COURSE_ID) private Integer attendingcourseid;
}
