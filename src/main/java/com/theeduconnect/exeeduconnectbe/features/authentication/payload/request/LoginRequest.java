package com.theeduconnect.exeeduconnectbe.features.authentication.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.authentication.messages.AuthenticationValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.regex.AuthenticationValidationRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Schema(name = "role", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = AuthenticationValidationMessages.INVALID_USER_ROLE) private Integer role;

    @Schema(name = "email", example = "abc@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationRegex.VALID_EMAIL,
            message = AuthenticationValidationMessages.INVALID_EMAIL)
    private String email;

    @Schema(
            name = "password",
            example = "Educonnect123!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationRegex.VALID_PASSWORD,
            message = AuthenticationValidationMessages.INVALID_PASSWORD)
    private String password;
}
