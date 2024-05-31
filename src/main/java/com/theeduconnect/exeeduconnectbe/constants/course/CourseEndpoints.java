package com.theeduconnect.exeeduconnectbe.constants.course;

public class CourseEndpoints {
    private static final String BASE_URL = "/api";
    public static final String GET_ALL_BY = BASE_URL + "/courses";
    public static final String CREATE = BASE_URL + "/course";
    public static final String JOIN_COURSE = BASE_URL + "/course/enroll";
    public static final String GET_BY_ID = BASE_URL + "/course/{id}";
}