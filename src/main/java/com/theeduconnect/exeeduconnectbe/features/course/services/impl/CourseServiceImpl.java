package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
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
    private GetAllByRequestServiceImpl getAllByRequestServiceImpl;
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
    public CourseServiceResponse getAllByRequest(GetAllByRequest request) {
        return getAllByRequestServiceImpl.Handle(request);
    }

    @Override
    public CourseServiceResponse create(NewCourseRequest request) {

        return createCourseServiceImpl.Handle(request);
    }

    private void InitializeChildServices() {
        getAllByRequestServiceImpl =
                new GetAllByRequestServiceImpl(
                        courseRepository, courseMapper, courseCategoryRepository, userRepository);
        createCourseServiceImpl =
                new CreateCourseServiceImpl(
                        courseRepository,
                        courseMapper,
                        courseCategoryRepository,
                        teacherRepository);
    }
}
