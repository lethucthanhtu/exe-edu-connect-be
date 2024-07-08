package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseDto;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.GetAllCoursesDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllCoursesByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import com.theeduconnect.exeeduconnectbe.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class GetAllCoursesByRequestServiceImpl {
    private final CourseRepository courseRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private List<CourseDto> courseDtos;
    private Page<Course> courses;
    private Pageable pageable;
    private GetAllCoursesByRequest getAllCoursesByRequest;
    private AverageStarsCalculatorServiceImpl averageStarsCalculatorServiceImpl;

    public GetAllCoursesByRequestServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            CourseCategoryRepository courseCategoryRepository,
            UserRepository userRepository,
            AverageStarsCalculatorServiceImpl averageStarsCalculatorServiceImpl) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseCategoryRepository = courseCategoryRepository;
        this.userRepository = userRepository;
        this.averageStarsCalculatorServiceImpl = averageStarsCalculatorServiceImpl;
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
        if (IsCourseCategoryNameValid() && IsCourseNameValid()) {
            FindByCourseCategoryNameAndCourseName();
        } else if (IsCourseCategoryNameValid()) {
            FindByCourseCategoryName();
        } else if (IsCourseNameValid()) {
            FindByCourseName();
        } else {
            courses = courseRepository.findAll(pageable);
        }
    }

    private boolean IsCourseCategoryNameValid() {
        String categoryName = getAllCoursesByRequest.getCategoryname();
        if (StringUtils.isEmpty(categoryName) || categoryName.equals("null")) {
            return false;
        }
        return true;
    }

    private boolean IsCourseNameValid() {
        String courseName = getAllCoursesByRequest.getCoursename();
        if (StringUtils.isEmpty(courseName) || courseName.equals("null")) {
            return false;
        }
        return true;
    }

    private void FindByCourseCategoryNameAndCourseName() {
        CourseCategory courseCategory =
                courseCategoryRepository.findByCategoryname(
                        getAllCoursesByRequest.getCategoryname());
        if (courseCategory == null) return;
        String normalizedCourseName = getAllCoursesByRequest.getCoursename().toLowerCase();
        Set<Course> coursesByCourseNameAndCourseCategoryName =
                courseCategory.getCourses().stream()
                        .filter(
                                course ->
                                        course.getName()
                                                .toLowerCase()
                                                .contains(normalizedCourseName))
                        .collect(Collectors.toSet());
        courses = ListUtils.SetToPaginatedList(coursesByCourseNameAndCourseCategoryName, pageable);
    }

    private void FindByCourseCategoryName() {
        CourseCategory courseCategory =
                courseCategoryRepository.findByCategoryname(
                        getAllCoursesByRequest.getCategoryname());
        if (courseCategory == null) return;
        courses = ListUtils.SetToPaginatedList(courseCategory.getCourses(), pageable);
    }

    private void FindByCourseName() {
        courses =
                courseRepository.findByNameContainsIgnoreCase(
                        getAllCoursesByRequest.getCoursename(), pageable);
    }

    private boolean AreCoursesAvailable() {
        if (courses == null) return false;
        return courses.stream().findAny().isPresent();
    }

    private void MapCoursesToCourseDtos() {
        for (Course course : courses) {
            CourseDto courseDto = courseMapper.CourseEntityToCourseDto(course);
            courseDto.setCategoryname(course.getCoursecategory().getCategoryname());
            courseDto.setTeacherid(course.getTeacher().getId());
            courseDto.setTeachername(GetTeacherNameByTeacherId(course.getTeacher().getId()));
            courseDto.setRating(
                    averageStarsCalculatorServiceImpl.GetAverageStarsByCourseId(course));
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
                new GetAllCoursesDto(courses.getTotalPages(), courseDtos));
    }

    private CourseServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
