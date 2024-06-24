package com.theeduconnect.exeeduconnectbe.features.user.dtos;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int userid;
    private String username;
    private String fullname;
    private Date dateofbirth;
    private String avatarurl;
    private String email;
    private String phone;
    private String address;
    private Boolean status;
    private Double balance;
    private int roleid;
}
