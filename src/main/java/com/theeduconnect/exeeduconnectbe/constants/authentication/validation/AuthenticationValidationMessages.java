package com.theeduconnect.exeeduconnectbe.constants.authentication.validation;

public class AuthenticationValidationMessages {
    public static final String INVALID_USER_ROLE =
            "The user role is not yet chosen. Please try again.";
    public static final String INVALID_EMAIL =
            "The email must follow the following format: abc@def.com.";
    public static final String INVALID_NAME =
            "The name must follow this format: John Dummy Doe, "
                    + "where each word starts with a capital letter.";
    public static final String INVALID_PASSWORD =
            "The password must be at least 8 characters in length,"
                    + " contain at least 1 capital letter, and a special character.";
    public static final String AUTHENTICATION_EXCEPTION =
            "When this happens, here are some possible causes: \n"
                    + " - The token might be expired \n"
                    + " - The login credentials might be non-existent in the system.\n"
                    + " - System error occurred.\n\n"
                    + "Check the log below for more details: ";
}
