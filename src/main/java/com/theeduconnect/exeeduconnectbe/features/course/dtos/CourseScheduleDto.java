package com.theeduconnect.exeeduconnectbe.features.course.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseScheduleDto {
    private String weekday;
    private String date;
    private String starttime;
    private String endtime;
    private String meeturl;
}
