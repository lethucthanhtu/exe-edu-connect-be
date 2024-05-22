package com.theeduconnect.exeeduconnectbe.constants.authentication.endpoints;

public class AuthenticationEndpoints {
    public static final String BASE_URL = "/api/auth";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String REGISTER_URL = BASE_URL + "/register";
    public static final String ROLES_URL = BASE_URL + "/roles";
    public static final String GOOGLE_LOGIN_URL = BASE_URL + "/google/login";
    public static final String LOGOUT_URL = BASE_URL + "/logout";
    public static final String[] ALLOWED_REQUEST_MATCHER_ENDPOINTS =
            new String[] {LOGIN_URL,REGISTER_URL,ROLES_URL,GOOGLE_LOGIN_URL,LOGOUT_URL, "/", "/oauth/**"};
}
