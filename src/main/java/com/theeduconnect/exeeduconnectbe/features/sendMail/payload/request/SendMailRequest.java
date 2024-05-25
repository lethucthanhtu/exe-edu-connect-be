package com.theeduconnect.exeeduconnectbe.features.sendMail.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMailRequest {
    @Schema(name = "email", example = "abc@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_EMAIL_REGEX,
            message = AuthenticationValidationMessages.INVALID_EMAIL)
    private String to;

    @Schema(name = "subject", example = "Test mail", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Subject is required")
    private String subject;

    @Schema(
            name = "body",
            example = "This is a test mail",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "body is required")
    private String body;
}
