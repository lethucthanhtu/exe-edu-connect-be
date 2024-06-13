package com.theeduconnect.exeeduconnectbe.features.courseSchedule.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CourseScheduleDto {
    private Integer id;
    private Instant starttime;
    private Integer duration;
    private String meeturl;
    private Integer courseId;
    private Integer studentId;
}
