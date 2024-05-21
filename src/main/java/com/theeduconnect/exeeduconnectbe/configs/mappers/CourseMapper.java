package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.entities.Course;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "teachername", ignore = true)
    @Mapping(target = "categoryname", ignore = true)
    CourseDto CourseEntityToCourseDto(Course course);

    Course NewCourseRequestToCourseEntity(NewCourseRequest request);
}
