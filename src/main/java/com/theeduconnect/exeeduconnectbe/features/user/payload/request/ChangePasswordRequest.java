package com.theeduconnect.exeeduconnectbe.features.user.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public class ChangePasswordRequest {
    @Schema(
            name = "currentpassword",
            example = "currentPassword!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentpassword;

    @Schema(
            name = "newpassword",
            example = "newPassword!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_PASSWORD_REGEX,
            message = AuthenticationValidationMessages.INVALID_PASSWORD)
    private String newpassword;

    // Getters and setters
    public String getCurrentpassword() {
        return currentpassword;
    }

    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
