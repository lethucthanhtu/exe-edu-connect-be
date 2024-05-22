package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.responseCodes.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.serviceMessages.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.entities.Course;
import com.theeduconnect.exeeduconnectbe.domain.entities.CourseCategory;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllCoursesByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import com.theeduconnect.exeeduconnectbe.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class GetAllByRequestServiceImpl {
    private final CourseRepository courseRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private List<CourseDto> courseDtos;
    private Page<Course> courses;
    private Pageable pageable;
    private GetAllCoursesByRequest getAllCoursesByRequest;

    public GetAllByRequestServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            CourseCategoryRepository courseCategoryRepository,
            UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseCategoryRepository = courseCategoryRepository;
        this.userRepository = userRepository;
    }

    public CourseServiceResponse Handle(GetAllCoursesByRequest getAllCoursesByRequest) {
        try {
            this.getAllCoursesByRequest = getAllCoursesByRequest;
            ResetPreviousSearchResults();
            ConvertPageRequestToPageable();
            FindAllCoursesByRequest();
            if (!AreCoursesAvailable()) return NoCoursesFoundResult();
            MapCoursesToCourseDtos();
            return GetAllCoursesSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void ResetPreviousSearchResults() {
        courseDtos = new ArrayList<>();
        courses = null;
        pageable = null;
    }

    private void ConvertPageRequestToPageable() {
        pageable =
                PageRequest.of(
                        getAllCoursesByRequest.getPage() - 1, getAllCoursesByRequest.getSize());
    }

    private void FindAllCoursesByRequest() {
        if (getAllCoursesByRequest.getCategoryname() != null) {
            FindByCategoryName();
            return;
        }
        courses = courseRepository.findAll(pageable);
    }

    private void FindByCategoryName() {
        CourseCategory courseCategory =
                courseCategoryRepository.findByCategoryname(
                        getAllCoursesByRequest.getCategoryname());
        if (courseCategory == null) return;
        courses = ListUtils.SetToPaginatedList(courseCategory.getCourses(), pageable);
    }

    private boolean AreCoursesAvailable() {
        if (courses == null) return false;
        return courses.stream().findAny().isPresent();
    }

    private void MapCoursesToCourseDtos() {
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

    private CourseServiceResponse GetAllCoursesSuccessfulResult() {
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
