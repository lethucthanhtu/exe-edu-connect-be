package com.theeduconnect.exeeduconnectbe.constants.authentication;

public class AuthenticationServiceMessages {
    public static final String NO_ROLES_FOUND = "No roles found in the database.";
    public static final String ALL_ROLES_FOUND = "Successfully retrieved all roles in the system.";
    public static final String EMAIL_IS_TAKEN =
            "This email is already taken. Please choose another one.";
    public static final String SUCCESSFUL_REGISTRATION = "Registration is successful.";
    public static final String SUCCESSFUL_LOGIN = "Successfully logged in.";
    public static final String INVALID_ROLE_RESULT =
            "The given role is non-existent in the system. Please try another one.";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong on the server side.";
    public static final String INVALID_LOGIN_CREDENTIALS =
            "Invalid login credentials detected. Check if role, email, and password are entered"
                    + " correctly.";
}
