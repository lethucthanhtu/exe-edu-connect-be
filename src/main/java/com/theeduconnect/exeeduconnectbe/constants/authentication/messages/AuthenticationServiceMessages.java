package com.theeduconnect.exeeduconnectbe.constants.authentication.messages;

public class AuthenticationServiceMessages {
    public static String NO_ROLES_FOUND = "No roles found in the database.";
    public static String ALL_ROLES_FOUND = "Successfully retrieved all roles in the system.";
    public static String EMAIL_IS_TAKEN = "This email is already taken. Please choose another one.";
    public static String SUCCESSFUL_REGISTRATION = "Registration is successful.";
    public static String SUCCESSFUL_LOGIN = "Successfully logged in.";
    public static String INVALID_ROLE_RESULT =
            "The given role is non-existent in the system. Please try another one.";
    public static String INTERNAL_SERVER_ERROR = "Something went wrong on the server side.";
    public static String INVALID_LOGIN_CREDENTIALS =
            "Invalid login credentials detected. Check if role, email, and password are entered"
                    + " correctly.";
}
