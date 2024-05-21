package com.theeduconnect.exeeduconnectbe.constants.authentication.validation;

public class AuthenticationValidationSpecifications {
    public static final String VALID_EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String VALID_NAME_REGEX = "^[A-Z][a-zA-Z]*(\\s[A-Z][a-zA-Z]*)*$";
    public static final String VALID_PASSWORD_REGEX =
            "^(?=.*[A-Z])(?=.*[!@#$%^&*()-_=+{}|;:',.<>?])(?=.*[a-zA-Z]).{8,}$";
}
