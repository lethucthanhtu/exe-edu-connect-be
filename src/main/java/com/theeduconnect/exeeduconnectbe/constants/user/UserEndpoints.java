package com.theeduconnect.exeeduconnectbe.constants.user;

public class UserEndpoints {
    public static final String GET_ALL_USER = "/api/users";
    public static final String GET_USER_BY_ID = "/{userId}";
    public static final String GET_USER_BY_JWT = "/user";
    public static final String UPDATE_USER = "/{userId}";
    public static final String DELETE_USER = "/{userId}";
    public static final String CHANGE_PASSWORD = "/{userId}/change-password";
    public static final String REQUEST_RESET_PASSWORD = "/request-reset-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String ALL_FEATURE_USER = "api/users/**";
}
