package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.configs.mappers.ScheduleMapper;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllCoursesByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.JoinCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.course.services.CourseService;
import com.theeduconnect.exeeduconnectbe.features.course.services.impl.create.CreateCourseServiceImpl;
import com.theeduconnect.exeeduconnectbe.features.course.services.impl.join.JoinCourseServiceImpl;
import com.theeduconnect.exeeduconnectbe.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private GetAllCoursesByRequestServiceImpl getAllCoursesByRequestServiceImpl;
    private GetCourseByIdServiceImpl getCourseByIdServiceImpl;
    private CreateCourseServiceImpl createCourseServiceImpl;
    private AverageStarsCalculatorServiceImpl averageStarsCalculatorService;
    private JoinCourseServiceImpl joinCourseServiceImpl;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    private final TeacherRepository teacherRepository;
    private final ScheduleMapper scheduleMapper;
    private final UserRepository userRepository;
    private final AttendingCourseRepository attendingCourseRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            ScheduleMapper scheduleMapper,
            CourseCategoryRepository courseCategoryRepository,
            CourseScheduleRepository courseScheduleRepository,
            TeacherRepository teacherRepository,
            UserRepository userRepository,
            AttendingCourseRepository attendingCourseRepository,
            StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.scheduleMapper = scheduleMapper;
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.attendingCourseRepository = attendingCourseRepository;
        this.studentRepository = studentRepository;
        InitializeChildServices();
    }

    @Override
    public CourseServiceResponse getAllByRequest(GetAllCoursesByRequest request) {
        return getAllCoursesByRequestServiceImpl.Handle(request);
    }

    @Override
    public CourseServiceResponse getById(int id) {
        return getCourseByIdServiceImpl.Handle(id);
    }

    @Override
    public CourseServiceResponse create(NewCourseRequest request) {

        return createCourseServiceImpl.Handle(request);
    }

    @Override
    public CourseServiceResponse join(JoinCourseRequest request) {
        return joinCourseServiceImpl.Handle(request);
    }

    private void InitializeChildServices() {
        averageStarsCalculatorService =
                new AverageStarsCalculatorServiceImpl(attendingCourseRepository);
        getAllCoursesByRequestServiceImpl =
                new GetAllCoursesByRequestServiceImpl(
                        courseRepository,
                        courseMapper,
                        courseCategoryRepository,
                        userRepository,
                        averageStarsCalculatorService);
        createCourseServiceImpl =
                new CreateCourseServiceImpl(
                        courseRepository,
                        courseMapper,
                        scheduleMapper,
                        courseCategoryRepository,
                        courseScheduleRepository,
                        teacherRepository);
        getCourseByIdServiceImpl =
                new GetCourseByIdServiceImpl(
                        courseRepository, courseMapper, userRepository, courseScheduleRepository);
        joinCourseServiceImpl =
                new JoinCourseServiceImpl(
                        attendingCourseRepository,
                        studentRepository,
                        courseRepository,
                        courseScheduleRepository);
    }
}
