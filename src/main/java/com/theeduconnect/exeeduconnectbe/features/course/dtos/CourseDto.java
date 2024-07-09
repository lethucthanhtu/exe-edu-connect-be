package com.theeduconnect.exeeduconnectbe.features.course.dtos;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
    private Integer id;

    private String name;

    private String description;

    private Double price;
    private Double rating;

    private LocalDate startdate;

    private LocalDate enddate;
    private List<CourseScheduleDto> coursescheduledtos;

    private Integer teacherid;
    private String teachername;

    private String categoryname;
}
