package com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseScheduleRequest {
    @Schema(name = "starttime", example = "2023-06-13 14:30:00")
    @NotNull(message = "starttime is required") private Instant starttime;

    @Schema(name = "duration", example = "90")
    @NotNull(message = "duration is required") private Integer duration;

    @Schema(name = "meeturl", example = "https://meet.example.com/abc123")
    @NotBlank(message = "meeturl is required")
    private String meeturl;

    @Schema(name = "courseId", example = "1")
    @NotNull(message = "courseId is required") private Integer courseId;

    @Schema(name = "studentId", example = "1")
    @NotNull(message = "studentId is required") private Integer studentId;
}
