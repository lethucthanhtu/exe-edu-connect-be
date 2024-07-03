package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.impl;

import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.request.ApproveAttendingCourseTransactionRequest;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.AttendingCourseService;
import com.theeduconnect.exeeduconnectbe.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class AttendingCourseServiceImpl implements AttendingCourseService {
    private GetAllAttendingCoursesByStudentIdServiceImpl getAllAttendingCoursesByUserIdServiceImpl;
    private ApproveAttendingCourseTransactionServiceImpl
            approveAttendingCourseTransactionServiceImpl;
    private final AttendingCourseRepository attendingCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private final TransactionRepository transactionRepository;
    private final CourseRepository courseRepository;

    public AttendingCourseServiceImpl(
            AttendingCourseRepository attendingCourseRepository,
            StudentRepository studentRepository,
            CourseScheduleRepository courseScheduleRepository,
            TransactionRepository transactionRepository,
            CourseRepository courseRepository) {
        this.attendingCourseRepository = attendingCourseRepository;
        this.studentRepository = studentRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.transactionRepository = transactionRepository;
        this.courseRepository = courseRepository;
        InitializeChildServices();
    }

    @Override
    public AttendingCourseServiceResponse getAllByStudentId(int userId) {
        return getAllAttendingCoursesByUserIdServiceImpl.Handle(userId);
    }

    @Override
    public AttendingCourseServiceResponse approveTransaction(
            ApproveAttendingCourseTransactionRequest request) {
        return approveAttendingCourseTransactionServiceImpl.Handle(request);
    }

    private void InitializeChildServices() {
        getAllAttendingCoursesByUserIdServiceImpl =
                new GetAllAttendingCoursesByStudentIdServiceImpl(
                        attendingCourseRepository, studentRepository);
        approveAttendingCourseTransactionServiceImpl =
                new ApproveAttendingCourseTransactionServiceImpl(
                        attendingCourseRepository,
                        courseScheduleRepository,
                        transactionRepository,
                        studentRepository,
                        courseRepository);
    }
}
