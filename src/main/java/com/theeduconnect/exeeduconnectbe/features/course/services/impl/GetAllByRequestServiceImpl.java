package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.responseCodes.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.serviceMessages.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.entities.Course;
import com.theeduconnect.exeeduconnectbe.domain.entities.CourseCategory;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private GetAllByRequest getAllByRequest;

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

    public CourseServiceResponse Handle(GetAllByRequest getAllByRequest) {
        try {
            this.getAllByRequest = getAllByRequest;
            ResetPreviousSearchResults();
            ConvertPageRequestToPageable();
            FindAllCoursesByRequest();
            if (!AreCoursesAvailable()) return NoCoursesFoundResult();
            MapCoursesToCourseDtos();
            return GetAllCoursesByPaginationSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void ResetPreviousSearchResults() {
        courseDtos = new ArrayList<>();
        courses = null;
        pageable = null;
    }
    ;

    private void ConvertPageRequestToPageable() {
        pageable = PageRequest.of(getAllByRequest.getPage() - 1, getAllByRequest.getSize());
    }

    private void FindAllCoursesByRequest() {
        if (getAllByRequest.getCategoryname() != null) {
            FindByCategoryName();
            return;
        }
        courses = courseRepository.findAll(pageable);
    }

    private void FindByCategoryName() {
        CourseCategory courseCategory =
                courseCategoryRepository.findByCategoryname(getAllByRequest.getCategoryname());
        if (courseCategory == null) return;
        List<Course> coursesByCategoryList = new ArrayList<>(courseCategory.getCourses());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), coursesByCategoryList.size());
        if (start > end) return;
        List<Course> pageContent = coursesByCategoryList.subList(start, end);
        courses = new PageImpl<>(pageContent, pageable, coursesByCategoryList.size());
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
