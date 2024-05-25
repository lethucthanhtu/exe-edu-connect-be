package com.theeduconnect.exeeduconnectbe.features.teacher.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.teacher.responseCodes.TeacherServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.teacher.serviceMessages.TeacherServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import com.theeduconnect.exeeduconnectbe.domain.Teacher;
import com.theeduconnect.exeeduconnectbe.features.teacher.dtos.TeacherByCourseCategoryDto;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.request.GetAllTeachersByRequest;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.response.TeacherServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.utils.ListUtils;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class GetByCourseCategoryServiceImpl {
    private GetAllTeachersByRequest request;
    private Set<TeacherByCourseCategoryDto> teacherByCourseCategoryDtos;
    private Page<Course> courses;
    private Pageable pageable;
    private final CourseCategoryRepository courseCategoryRepository;

    public GetByCourseCategoryServiceImpl(CourseCategoryRepository courseCategoryRepository) {
        this.courseCategoryRepository = courseCategoryRepository;
    }

    public TeacherServiceResponse Handle(GetAllTeachersByRequest request) {
        try {
            this.request = request;
            ResetPreviousSearchResults();
            ConvertPageRequestToPageable();
            FindCoursesByCourseCategory();
            ExtractTeacherDetailsFromCourses();
            return GetAllTeachersSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void ResetPreviousSearchResults() {
        teacherByCourseCategoryDtos = new HashSet<>();
        pageable = null;
    }

    private void ConvertPageRequestToPageable() {
        pageable = PageRequest.of(request.getPage() - 1, request.getSize());
    }

    private void FindCoursesByCourseCategory() {
        CourseCategory courseCategory =
                courseCategoryRepository.findByCategoryname(request.getCategoryname());
        if (courseCategory == null) return;
        courses = ListUtils.SetToPaginatedList(courseCategory.getCourses(), pageable);
    }

    private void ExtractTeacherDetailsFromCourses() {
        for (Course course : courses) {
            Teacher teacher = course.getTeacher();
            TeacherByCourseCategoryDto teacherByCourseCategoryDto =
                    new TeacherByCourseCategoryDto(
                            teacher.getId(),
                            teacher.getUser().getFullname(),
                            course.getStartdate());
            teacherByCourseCategoryDtos.add(teacherByCourseCategoryDto);
        }
    }

    private TeacherServiceResponse GetAllTeachersSuccessfulResult() {
        return new TeacherServiceResponse(
                TeacherServiceHttpResponseCodes.FOUND_ALL_TEACHERS,
                TeacherServiceMessages.FOUND_ALL_TEACHERS,
                teacherByCourseCategoryDtos);
    }

    private TeacherServiceResponse InternalServerErrorResult(Exception e) {
        return new TeacherServiceResponse(
                TeacherServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                TeacherServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
