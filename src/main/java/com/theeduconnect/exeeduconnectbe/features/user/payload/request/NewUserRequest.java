package com.theeduconnect.exeeduconnectbe.features.user.payload.request;

import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.validation.AuthenticationValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    @Schema(name = "username", example = "johndoe")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(name = "fullname", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_NAME_REGEX,
            message = AuthenticationValidationMessages.INVALID_NAME)
    @NotNull(message = "Full Name is required") private String fullname;

    @Schema(name = "dateofbirth", example = "1990-01-01")
    @NotNull(message = "Date of Birth is required") private LocalDate dateofbirth;

    @Schema(name = "avatarurl", example = "http://example.com/avatar.jpg")
    private String avatarurl;

    @Schema(name = "email", example = "abc@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = AuthenticationValidationSpecifications.VALID_EMAIL_REGEX,
            message = AuthenticationValidationMessages.INVALID_EMAIL)
    private String email;

    @Schema(name = "phone", example = "1234567890")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone;

    @Schema(name = "address", example = "123 Main St, Anytown, USA")
    @NotBlank(message = "Address is required")
    private String address;

    @Schema(name = "status", example = "true")
    private Boolean status;

    @Schema(name = "balance", example = "100.0")
    private Double balance;

    @Schema(name = "roleid", example = "1")
    private int roleid;
}
