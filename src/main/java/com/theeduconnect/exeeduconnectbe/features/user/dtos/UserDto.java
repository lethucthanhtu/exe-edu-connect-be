package com.theeduconnect.exeeduconnectbe.features.user.dtos;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int userId;
    private String username;
    private String fullName;
    private Date dateOfBirth;
    private String avatarUrl;
    private String email;
    private String phone;
    private String address;
    private Boolean status;
    private Double balance;
    private int roleId;
}
