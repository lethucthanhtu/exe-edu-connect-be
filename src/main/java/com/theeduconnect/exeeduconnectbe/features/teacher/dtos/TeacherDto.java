package com.theeduconnect.exeeduconnectbe.features.teacher.dtos;

import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeacherDto {
    private int teacherId;
    private String teacherName;
    private LocalDate startDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto teacherDto = (TeacherDto) o;
        return Objects.equals(teacherId, teacherDto.getTeacherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId);
    }
}
