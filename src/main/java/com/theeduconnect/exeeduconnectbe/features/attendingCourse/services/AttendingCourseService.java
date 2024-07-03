package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services;

import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;

public interface AttendingCourseService {
    AttendingCourseServiceResponse getAllByStudentId(int studentId);

    AttendingCourseServiceResponse approveTransaction(int attendingCourseId);
}
