package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class NewCourseScheduleRequest {
    @Schema(
            name = "starttime",
            example = "2024-05-24T08:30:00.000Z",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = CourseValidationMessages.INVALID_START_TIME) private Instant starttime;

    @Schema(name = "duration", example = "60", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(
            min = CourseValidationSpecifications.MIN_COURSE_DURATION_IN_MINUTES,
            max = CourseValidationSpecifications.MAX_COURSE_DURATION_IN_MINUTES,
            message = CourseValidationMessages.INVALID_COURSE_DURATION_IN_MINUTES)
    private Integer duration;

}
