package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.attendingCourse.AttendingCourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.*;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.request.ApproveAttendingCourseTransactionRequest;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

public class ApproveAttendingCourseTransactionServiceImpl {
    private final CourseScheduleRepository courseScheduleRepository;
    private final AttendingCourseRepository attendingCourseRepository;
    private final TransactionRepository transactionRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private ApproveAttendingCourseTransactionRequest request;
    private Student student;
    private Course course;
    private Transaction transaction;
    private Optional<AttendingCourse> attendingCourseOptional;

    public ApproveAttendingCourseTransactionServiceImpl(
            AttendingCourseRepository attendingCourseRepository,
            CourseScheduleRepository courseScheduleRepository,
            TransactionRepository transactionRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.attendingCourseRepository = attendingCourseRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.transactionRepository = transactionRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public AttendingCourseServiceResponse Handle(ApproveAttendingCourseTransactionRequest request) {
        try {
            this.request = request;
            attendingCourseOptional =
                    attendingCourseRepository.findById(request.getAttendingcourseid());
            if (attendingCourseOptional.isEmpty()) return AttendingCourseNotFoundResult();
            AttendingCourse attendingCourse = attendingCourseOptional.get();
            if (attendingCourse.getStatus()) return AttendingCourseAlreadyApprovedResult();
            attendingCourse.setStatus(true);
            attendingCourseRepository.save(attendingCourse);
            FindTransactionDetails();
            AssignStudentToCourseSchedules();
            return ApproveAttendingCourseTransactionSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void FindTransactionDetails() {
        transaction = transactionRepository.findById(request.getTransactionid()).get();
        int studentId = transaction.getUser().getId();
        int courseId = transaction.getCourseid();
        student = studentRepository.findById(studentId).get();
        course = courseRepository.findById(courseId).get();
    }

    private void AssignStudentToCourseSchedules() {
        List<CourseSchedule> courseScheduleList = courseScheduleRepository.findByCourse(course);
        for (CourseSchedule courseSchedule : courseScheduleList) {
            courseSchedule.setStudent(student);
        }
        courseScheduleRepository.saveAll(courseScheduleList);
    }

    private AttendingCourseServiceResponse AttendingCourseNotFoundResult() {
        return new AttendingCourseServiceResponse(
                HttpStatus.BAD_REQUEST.value(),
                AttendingCourseServiceMessages.ATTENDING_COURSE_NOT_FOUND,
                null);
    }

    private AttendingCourseServiceResponse AttendingCourseAlreadyApprovedResult() {
        return new AttendingCourseServiceResponse(
                HttpStatus.BAD_REQUEST.value(),
                AttendingCourseServiceMessages.ATTENDING_COURSE_TRANSACTION_ALREADY_APPROVED,
                null);
    }

    private AttendingCourseServiceResponse ApproveAttendingCourseTransactionSuccessfulResult() {
        return new AttendingCourseServiceResponse(
                HttpStatus.OK.value(),
                AttendingCourseServiceMessages.APPROVE_TRANSACTION_SUCCESSFUL,
                null);
    }

    private AttendingCourseServiceResponse InternalServerErrorResult(Exception e) {
        return new AttendingCourseServiceResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                AttendingCourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
