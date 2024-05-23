package com.theeduconnect.exeeduconnectbe.features.user.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    @Schema(name = "email", example = "abc@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_EMAIL_REGEX,
            message = AuthenticationValidationMessages.INVALID_EMAIL)
    private String email;
    @Schema(name = "token", example = "38edc86a-9aea-4b6b-8809-534a9ef69985")

    private String token;
    @Schema(
            name = "newPassword",
            example = "newPassword!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_PASSWORD_REGEX,
            message = AuthenticationValidationMessages.INVALID_PASSWORD)
    private String newPassword;

}
