package com.theeduconnect.exeeduconnectbe.constants.authentication.messages;

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
}
