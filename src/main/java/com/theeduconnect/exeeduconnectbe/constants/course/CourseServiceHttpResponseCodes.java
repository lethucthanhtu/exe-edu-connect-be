package com.theeduconnect.exeeduconnectbe.constants.course;

public class CourseServiceHttpResponseCodes {
    public static final int FOUND_ALL_COURSES = 200;
    public static final int FOUND_COURSE_BY_ID_SUCCESSFUL = 200;
    public static final int CREATE_COURSE_SUCCESSFUL = 201;
    public static final int JOIN_COURSE_SUCCESSFUL = 201;
    public static final int COURSE_TAKEN = 409;
    public static final int NO_COURSES_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int START_DATE_BEFORE_END_DATE = 400;
    public static final int INVALID_COURSE_CATEGORY_ID = 404;
    public static final int INVALID_COURSE_CATEGORY_NAME = 404;
    public static final int INVALID_START_TIME = 404;
    public static final int COURSE_SCHEDULE_NOT_FOUND = 404;
}
