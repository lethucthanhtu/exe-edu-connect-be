package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.attendingCourse.AttendingCourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.AttendingCourse;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.AttendingCourseRepository;
import java.util.Optional;
import org.springframework.http.HttpStatus;

public class ApproveAttendingCourseTransactionServiceImpl {
    private final AttendingCourseRepository attendingCourseRepository;
    private Optional<AttendingCourse> attendingCourseOptional;

    public ApproveAttendingCourseTransactionServiceImpl(
            AttendingCourseRepository attendingCourseRepository) {
        this.attendingCourseRepository = attendingCourseRepository;
    }

    public AttendingCourseServiceResponse Handle(int attendingCourseId) {
        try {
            attendingCourseOptional = attendingCourseRepository.findById(attendingCourseId);
            if (attendingCourseOptional.isEmpty()) return AttendingCourseNotFoundResult();

            AttendingCourse attendingCourse = attendingCourseOptional.get();
            if (attendingCourse.getStatus()) return AttendingCourseAlreadyApprovedResult();
            attendingCourse.setStatus(true);
            attendingCourseRepository.save(attendingCourse);
            return ApproveAttendingCourseTransactionSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
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
