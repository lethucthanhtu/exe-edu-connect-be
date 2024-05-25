package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseScheduleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(target = "starttime", ignore = true)
    CourseSchedule NewCourseScheduleRequestToCourseScheduleEntity(NewCourseScheduleRequest request);
}
