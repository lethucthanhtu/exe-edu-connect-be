package com.theeduconnect.exeeduconnectbe.features.user.payload.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    @NotNull(message = "Username is required") private String username;

    @NotNull(message = "Full Name is required") private String fullName;

    @NotNull(message = "Date of Birth is required") private LocalDate dateOfBirth;

    private String avatarUrl;

    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    @NotNull(message = "Password is required") private String password;

    private String address;

    private Boolean status;

    private Double balance;

    private int roleId;
}
