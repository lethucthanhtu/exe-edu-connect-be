package com.theeduconnect.exeeduconnectbe.features.certificate.dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private LocalDate startdate;

    private LocalDate enddate;

    private String teachername;

    private String categoryname;
}
