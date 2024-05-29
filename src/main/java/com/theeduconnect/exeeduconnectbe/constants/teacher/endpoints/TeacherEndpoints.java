package com.theeduconnect.exeeduconnectbe.constants.teacher.endpoints;

public class TeacherEndpoints {
    private static final String BASE_URL = "/api";
    public static final String GET_ALL_BY_COURSE_CATEGORY = BASE_URL + "/teachers";
    public static final String GET_BY_ID = BASE_URL + "/teacher/{id}";
    public static final String[] ALLOWED_REQUEST_MATCHER_ENDPOINTS =
            new String[] {GET_ALL_BY_COURSE_CATEGORY, GET_BY_ID};
}
