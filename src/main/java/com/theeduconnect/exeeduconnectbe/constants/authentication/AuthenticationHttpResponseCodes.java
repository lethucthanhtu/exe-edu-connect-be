package com.theeduconnect.exeeduconnectbe.constants.authentication;

public class AuthenticationHttpResponseCodes {
    public static final int NO_ROLES_FOUND = 404;
    public static final int ALL_ROLES_FOUND = 200;
    public static final int SUCCESSFUL_LOGIN = 200;
    public static final int EMAIL_IS_TAKEN = 409;
    public static final int SUCCESSFUL_REGISTRATION = 201;
    public static final int INVALID_ROLE_RESULT = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int INVALID_LOGIN_CREDENTIALS = 401;
}
