package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class NewCourseScheduleRequest {
    @Schema(name = "dayofweek", example = "MONDAY", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = CourseValidationMessages.INVALID_DAY_OF_WEEK) private DayOfWeek dayofweek;

    @Schema(name = "starttime", example = "08:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = CourseValidationMessages.INVALID_START_TIME) private LocalTime starttime;

    @Schema(name = "duration", example = "60", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(
            min = CourseValidationSpecifications.MIN_COURSE_DURATION_IN_MINUTES,
            max = CourseValidationSpecifications.MAX_COURSE_DURATION_IN_MINUTES,
            message = CourseValidationMessages.INVALID_COURSE_DURATION_IN_MINUTES)
    private Integer duration;
}
