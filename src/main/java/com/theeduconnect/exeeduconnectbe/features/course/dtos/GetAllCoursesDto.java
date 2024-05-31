package com.theeduconnect.exeeduconnectbe.features.course.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllCoursesDto {
    private int totalPageCount;
    private List<CourseDto> courseDtos;
}