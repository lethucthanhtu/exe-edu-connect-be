package com.theeduconnect.exeeduconnectbe.constants.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationEndpoints {
    @Value("${educonnect.fe.url}")
    private static String eduConnectFEUrl;

    private static final String BASE_URL = "/api/auth";
    private static final String OAUTH2_GOOGLE_AUTH_SERVER_URL = "/login";
    private static final String OAUTH2_ALL_URL = "/oauth/**";
    private static final String ANY_URL = "/";
    public static final String REGISTER_URL = BASE_URL + "/register";
    public static final String GOOGLE_REGISTER_URL = BASE_URL + "/register/google/{roleId}";
    public static final String ROLES_URL = BASE_URL + "/roles";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String GOOGLE_LOGIN_URL = BASE_URL + "/login/google";
    public static final String GOOGLE_REDIRECT_URL = "/oauth2/authorization/google";
    public static final String LOGOUT_URL = BASE_URL + "/logout";

    public static final String[] ALLOWED_REQUEST_MATCHER_ENDPOINTS =
            new String[] {
                LOGIN_URL,
                REGISTER_URL,
                GOOGLE_REGISTER_URL,
                GOOGLE_REDIRECT_URL,
                ROLES_URL,
                GOOGLE_LOGIN_URL,
                LOGOUT_URL,
                ANY_URL,
                OAUTH2_ALL_URL,
                OAUTH2_GOOGLE_AUTH_SERVER_URL
            };
}
