package com.theeduconnect.exeeduconnectbe.features.certificate.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllCoursesDto {
    private int totalpagecount;
    private List<CourseDto> coursedtos;
}
