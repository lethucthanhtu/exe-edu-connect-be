package com.theeduconnect.exeeduconnectbe.features.courseCategory.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCategoryDto {
    private Integer id;
    private String categoryname;
    private int total;
    private String description;
}
