package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.responseCodes.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.serviceMessages.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseDto;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.GetAllCoursesDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;

import java.util.Optional;

public class GetCourseByIdServiceImpl {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;
    private Optional<Course> courseOptional;
    private int courseId;
    private CourseDto courseDto;

    public GetCourseByIdServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper,UserRepository userRepository){
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.userRepository = userRepository;
    }
    public CourseServiceResponse Handle(int courseId){
        this.courseId = courseId;
        try{
            if(!IsCourseAvailableInDatabase()) return CourseNotFoundResult();
            MapCourseEntityToCourseDto();
            return GetCourseByIdSuccessfulResult();
        }catch(Exception e){
            return InternalServerErrorResult(e);
        }
    }
    private boolean IsCourseAvailableInDatabase(){
        courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty()) return false;
        return true;
    }
    private void MapCourseEntityToCourseDto(){
        Course course = courseOptional.get();
        courseDto = courseMapper.CourseEntityToCourseDto(course);
        courseDto.setCategoryname(course.getCoursecategory().getCategoryname());
        courseDto.setTeachername(GetTeacherNameByTeacherId(course.getTeacher().getId()));
    }
    private String GetTeacherNameByTeacherId(int teacherId) {
        return userRepository.findById(teacherId).get().getFullname();
    }
    private CourseServiceResponse GetCourseByIdSuccessfulResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.FOUND_COURSE_BY_ID_SUCCESSFUL,
                CourseServiceMessages.FOUND_COURSE_BY_ID_SUCCESSFUL,
                courseDto);
    }
    private CourseServiceResponse CourseNotFoundResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.NO_COURSES_FOUND,
                CourseServiceMessages.NO_COURSES_FOUND,
                null);
    }

    private CourseServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
