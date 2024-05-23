package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.configs.mappers.ScheduleMapper;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllCoursesByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.course.services.CourseService;
import com.theeduconnect.exeeduconnectbe.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private GetAllByRequestServiceImpl getAllByRequestServiceImpl;
    private CreateCourseServiceImpl createCourseServiceImpl;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private final TeacherRepository teacherRepository;
    private final ScheduleMapper scheduleMapper;
    private final UserRepository userRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            ScheduleMapper scheduleMapper,
            CourseCategoryRepository courseCategoryRepository,
            CourseScheduleRepository courseScheduleRepository,
            TeacherRepository teacherRepository,
            UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.scheduleMapper = scheduleMapper;
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        InitializeChildServices();
    }

    @Override
    public CourseServiceResponse getAllByRequest(GetAllCoursesByRequest request) {
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
                        scheduleMapper,
                        courseCategoryRepository,
                        courseScheduleRepository,
                        teacherRepository);
    }
}
