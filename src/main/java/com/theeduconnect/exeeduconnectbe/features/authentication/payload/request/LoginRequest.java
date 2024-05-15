package com.theeduconnect.exeeduconnectbe.features.authentication.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.authentication.messages.AuthenticationValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.regex.AuthenticationValidationRegex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull(message = AuthenticationValidationMessages.INVALID_USER_ROLE) private Integer role;

    @Pattern(
            regexp = AuthenticationValidationRegex.VALID_EMAIL,
            message = AuthenticationValidationMessages.INVALID_EMAIL)
    private String email;

    @Pattern(
            regexp = AuthenticationValidationRegex.VALID_PASSWORD,
            message = AuthenticationValidationMessages.INVALID_PASSWORD)
    private String password;
}
