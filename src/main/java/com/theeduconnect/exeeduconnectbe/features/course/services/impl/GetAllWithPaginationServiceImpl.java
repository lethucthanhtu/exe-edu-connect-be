package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.responseCodes.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.serviceMessages.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.entities.Course;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.PaginationRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class GetAllWithPaginationServiceImpl {
    private final CourseRepository courseRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private List<CourseDto> courseDtos;
    private Page<Course> courses;
    private PaginationRequest paginationRequest;

    public GetAllWithPaginationServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            CourseCategoryRepository courseCategoryRepository,
            UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseCategoryRepository = courseCategoryRepository;
        this.userRepository = userRepository;
    }

    public CourseServiceResponse Handle(PaginationRequest paginationRequest) {
        try {
            this.paginationRequest = paginationRequest;
            FindAllCoursesByPagination();
            if (!AreCoursesAvailable()) return NoCoursesFoundResult();
            MapCoursesToCourseDtos();
            return GetAllCoursesByPaginationSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void FindAllCoursesByPagination() {
        Pageable pageable =
                PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize());
        courses = courseRepository.findAll(pageable);
    }

    private boolean AreCoursesAvailable() {
        return courses.stream().findAny().isPresent();
    }

    private void MapCoursesToCourseDtos() {
        courseDtos = new ArrayList<CourseDto>();
        for (Course course : courses) {
            CourseDto courseDto = courseMapper.CourseEntityToCourseDto(course);
            courseDto.setCategoryname(course.getCoursecategory().getCategoryname());
            courseDto.setTeachername(GetTeacherNameByTeacherId(course.getTeacher().getId()));
            courseDtos.add(courseDto);
        }
    }

    private String GetTeacherNameByTeacherId(int teacherId) {
        return userRepository.findById(teacherId).get().getFullname();
    }

    private CourseServiceResponse NoCoursesFoundResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.NO_COURSES_FOUND,
                CourseServiceMessages.NO_COURSES_FOUND,
                null);
    }

    private CourseServiceResponse GetAllCoursesByPaginationSuccessfulResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.FOUND_ALL_COURSES,
                CourseServiceMessages.FOUND_ALL_COURSES,
                courseDtos);
    }

    private CourseServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
