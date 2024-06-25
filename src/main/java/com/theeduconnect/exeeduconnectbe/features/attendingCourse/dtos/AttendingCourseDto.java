package com.theeduconnect.exeeduconnectbe.features.attendingCourse.dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendingCourseDto {
    private String coursecategoryname;
    private String coursename;
    private LocalDate startdate;
    private LocalDate enddate;
    private boolean islearning;
}
