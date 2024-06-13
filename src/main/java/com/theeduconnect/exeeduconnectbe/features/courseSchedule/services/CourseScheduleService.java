package com.theeduconnect.exeeduconnectbe.features.courseSchedule.services;

import com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.request.CourseScheduleRequest;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.response.CourseScheduleResponse;

public interface CourseScheduleService {
    CourseScheduleResponse getAllCourseSchedules();

    CourseScheduleResponse getCourseScheduleById(int id);

    CourseScheduleResponse createCourseSchedule(CourseScheduleRequest request);

    CourseScheduleResponse updateCourseSchedule(int id, CourseScheduleRequest request);

    CourseScheduleResponse deleteCourseSchedule(int id);
    CourseScheduleResponse getAllSchedulesByStudentId(int studentId);
    CourseScheduleResponse getAllSchedulesByStudentIdAndCourseId(int studentId, int courseId);
}
