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
}
