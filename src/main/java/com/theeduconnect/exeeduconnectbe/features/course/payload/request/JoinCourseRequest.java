package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.course.validation.CourseValidationSpecifications;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinCourseRequest {
    @JsonIgnore
    private int studentId;
    @Min(value = CourseValidationSpecifications.MIN_COURSE_ID, message = CourseValidationMessages.INVALID_COURSE_ID)
    private int courseId;
    @Min(value = CourseValidationSpecifications.MIN_COURSE_SCHEDULE_ID, message = CourseValidationMessages.INVALID_COURSE_SCHEDULE_ID)
    private int courseScheduleId;
}
