package com.theeduconnect.exeeduconnectbe.constants.user.responseCodes;

public class UserServiceHttpResponseCodes {
    public static final int FOUND_ALL_USER = 200;

    public static final int CREATE_USER_SUCCESSFUL = 201;
    public static final int ROLE_NOT_FOUND = 404;

    public static final int GET_USER_BY_ID_SUCCESSFUL = 200;
    public static final int USER_NOT_FOUND = 404;

    public static final int UPDATED_USER_SUCCESSFUL = 200;
    public static final int DELETED_USER_SUCCESSFUL = 200;

    public static final int CURRENT_PASSWORD_INCORRECT = 400;
    public static final int CHANGE_PASSWORD_SUCCESSFUL = 200;

    public static final int RESET_PASSWORD_EMAIL_SENT = 200;
    public static final int RESET_PASSWORD_SUCCESSFUL = 200;

    public static final int INVALID_RESET_PASSWORD_TOKEN = 401;
}
