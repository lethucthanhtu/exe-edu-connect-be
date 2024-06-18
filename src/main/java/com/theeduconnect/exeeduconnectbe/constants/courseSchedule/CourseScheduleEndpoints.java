package com.theeduconnect.exeeduconnectbe.constants.courseSchedule;

public class CourseScheduleEndpoints {
    public static final String COURSE_SCHEDULE_ROOT = "/api/course-schedules";
    public static final String GET_SCHEDULE_BY_ID = "/{id}";
    public static final String UPDATE_SCHEDULE_BY_ID = "/{id}";
    public static final String DELETE_SCHEDULE_BY_ID = "/{id}";
    public static final String GET_ALL_SCHEDULES_BY_STUDENT_ID = "/student/{studentId}";
    public static final String GET_ALL_SCHEDULES_BY_STUDENT_ID_AND_COURSE_ID =
            "/student/{studentId}/course/{courseId}";
}
