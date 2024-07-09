package com.theeduconnect.exeeduconnectbe.features.course.dtos;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseScheduleDto {
    private String weekday;
    private String starttime;
    private String endtime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseScheduleDto courseScheduleDto = (CourseScheduleDto) o;
        return Objects.equals(weekday, courseScheduleDto.getWeekday());
    }

    @Override
    public int hashCode() {
        return Objects.hash(weekday);
    }
}
