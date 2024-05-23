package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCourseRequest {

    @Schema(
            name = "name",
            example = "Mathematics for Dummies",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(
            min = CourseValidationSpecifications.MIN_NAME_LENGTH,
            max = CourseValidationSpecifications.MAX_NAME_LENGTH,
            message = CourseValidationMessages.INVALID_COURSE_NAME)
    private String name;

    @Schema(
            name = "description",
            example = "A course that teaches mathematics for everyone",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(
            min = CourseValidationSpecifications.MIN_DESCRIPTION_LENGTH,
            max = CourseValidationSpecifications.MAX_DESCRIPTION_LENGTH,
            message = CourseValidationMessages.INVALID_DESCRIPTION)
    @NotNull(message = CourseValidationMessages.INVALID_DESCRIPTION) private String description;

    @Schema(name = "price", example = "500000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive(message = CourseValidationMessages.INVALID_PRICE) private Double price;

    @Schema(name = "startdate", example = "2024-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = CourseValidationMessages.INVALID_START_DATE) private LocalDate startdate;

    @Schema(name = "enddate", example = "2024-12-31", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = CourseValidationMessages.INVALID_END_DATE) private LocalDate enddate;

    @JsonIgnore private Integer teacherid;

    @Schema(name = "categoryid", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = CourseValidationMessages.INVALID_CATEGORY_ID) private Integer categoryid;

    @Schema(
            name = "weekdays",
            example = "[\"MON\",\"TUE\",\"FRI\"]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = CourseValidationMessages.INVALID_WEEKDAYS) private List<String> weekdays;
}
