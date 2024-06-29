/* (C) 2024 */
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
public class RegisterRequest {
    @Schema(name = "role", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = AuthenticationValidationMessages.INVALID_USER_ROLE) private Integer role;

    @Schema(name = "email", example = "abc@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_EMAIL_REGEX,
            message = AuthenticationValidationMessages.INVALID_EMAIL)
    private String email;

    @Schema(
            name = "fullname",
            example = "Edu Connect Is Awesome",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_NAME_REGEX,
            message = AuthenticationValidationMessages.INVALID_NAME)
    private String fullname;

    @Schema(
            name = "password",
            example = "Educonnect123!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_PASSWORD_REGEX,
            message = AuthenticationValidationMessages.INVALID_PASSWORD)
    private String password;
}
