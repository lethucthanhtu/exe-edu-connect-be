package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.responseCodes.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.serviceMessages.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.entities.Course;
import com.theeduconnect.exeeduconnectbe.domain.entities.CourseCategory;
import com.theeduconnect.exeeduconnectbe.domain.entities.Teacher;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import java.util.Optional;

public class CreateCourseServiceImpl {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseMapper courseMapper;
    private NewCourseRequest request;
    private Course course;
    private CourseCategory courseCategory;

    public CreateCourseServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            CourseCategoryRepository courseCategoryRepository,
            TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.teacherRepository = teacherRepository;
        this.courseCategoryRepository = courseCategoryRepository;
    }

    public CourseServiceResponse Handle(NewCourseRequest request) {
        try {
            this.request = request;
            if (!IsStartDateBeforeEndDate()) return StartDateNotBeforeEndDateResult();
            if (!IsCourseCategoryIdValid()) return InvalidCourseCategoryIdResult();
            MapNewCourseRequestToCourseEntity();
            courseRepository.save(course);
            return CreateCourseSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean IsStartDateBeforeEndDate() {
        if (request.getStartdate().isBefore(request.getEnddate())) return true;
        return false;
    }

    private boolean IsCourseCategoryIdValid() {
        Optional<CourseCategory> courseCategoryOptional =
                courseCategoryRepository.findById(request.getCategoryid());
        if (courseCategoryOptional.isEmpty()) return false;
        courseCategory = courseCategoryOptional.get();
        return true;
    }

    private void MapNewCourseRequestToCourseEntity() {
        course = courseMapper.NewCourseRequestToCourseEntity(request);
        course.setCoursecategory(courseCategory);
        Teacher teacher = teacherRepository.findById(request.getTeacherid()).get();
        course.setTeacher(teacher);
    }

    private CourseServiceResponse CreateCourseSuccessfulResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.CREATE_COURSE_SUCCESSFUL,
                CourseServiceMessages.CREATE_COURSE_SUCCESSFUL,
                null);
    }

    private CourseServiceResponse StartDateNotBeforeEndDateResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.START_DATE_BEFORE_END_DATE,
                CourseServiceMessages.START_DATE_BEFORE_END_DATE,
                null);
    }

    private CourseServiceResponse InvalidCourseCategoryIdResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INVALID_COURSE_CATEGORY_ID,
                CourseServiceMessages.INVALID_COURSE_CATEGORY_ID,
                null);
    }

    private CourseServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
