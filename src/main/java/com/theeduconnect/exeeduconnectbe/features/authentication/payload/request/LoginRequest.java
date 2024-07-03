package com.theeduconnect.exeeduconnectbe.features.authentication.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Schema(name = "email", example = "abc@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_EMAIL_REGEX,
            message = AuthenticationValidationMessages.INVALID_EMAIL)
    private String email;

    @Schema(
            name = "password",
            example = "Educonnect123!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = AuthenticationValidationMessages.PASSWORD_EMPTY) private String password;
}
