package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services;

import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.request.ApproveAttendingCourseTransactionRequest;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;

public interface AttendingCourseService {
    AttendingCourseServiceResponse getAllByStudentId(int studentId);

    AttendingCourseServiceResponse approveTransaction(
            ApproveAttendingCourseTransactionRequest request);
}
