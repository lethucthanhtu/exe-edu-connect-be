package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.impl;

import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.AttendingCourseService;
import com.theeduconnect.exeeduconnectbe.repositories.AttendingCourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendingCourseServiceImpl implements AttendingCourseService {
    private GetAllAttendingCoursesByStudentIdServiceImpl getAllAttendingCoursesByUserIdServiceImpl;
    private final AttendingCourseRepository attendingCourseRepository;
    private final StudentRepository studentRepository;

    public AttendingCourseServiceImpl(
            AttendingCourseRepository attendingCourseRepository,
            StudentRepository studentRepository) {
        this.attendingCourseRepository = attendingCourseRepository;
        this.studentRepository = studentRepository;
        InitializeChildServices();
    }

    @Override
    public AttendingCourseServiceResponse getAllByStudentId(int userId) {
        return getAllAttendingCoursesByUserIdServiceImpl.Handle(userId);
    }

    private void InitializeChildServices() {
        getAllAttendingCoursesByUserIdServiceImpl =
                new GetAllAttendingCoursesByStudentIdServiceImpl(
                        attendingCourseRepository, studentRepository);
    }
}
