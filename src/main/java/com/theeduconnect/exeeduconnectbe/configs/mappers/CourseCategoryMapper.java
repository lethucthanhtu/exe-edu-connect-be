package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.dtos.CourseCategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseCategoryMapper {

    CourseCategoryDto CourseCategoryEntityToCourseCategoryDto(CourseCategory courseCategory);
}
