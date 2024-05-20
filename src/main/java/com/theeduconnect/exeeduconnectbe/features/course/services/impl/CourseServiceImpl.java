package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.PaginationRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.course.services.CourseService;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private GetAllWithPaginationServiceImpl getAllWithPaginationServiceImpl;
    private CreateCourseServiceImpl createCourseServiceImpl;
    private final CourseCategoryRepository courseCategoryRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            CourseCategoryRepository courseCategoryRepository,
            TeacherRepository teacherRepository,
            UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseCategoryRepository = courseCategoryRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        InitializeChildServices();
    }

    @Override
    public CourseServiceResponse getAllWithPagination(PaginationRequest pageRequest) {
        return getAllWithPaginationServiceImpl.Handle(pageRequest);
    }

    @Override
    public CourseServiceResponse create(NewCourseRequest request) {

        return createCourseServiceImpl.Handle(request);
    }

    private void InitializeChildServices() {
        getAllWithPaginationServiceImpl =
                new GetAllWithPaginationServiceImpl(
                        courseRepository, courseMapper, courseCategoryRepository, userRepository);
        createCourseServiceImpl =
                new CreateCourseServiceImpl(
                        courseRepository,
                        courseMapper,
                        courseCategoryRepository,
                        teacherRepository);
    }
}