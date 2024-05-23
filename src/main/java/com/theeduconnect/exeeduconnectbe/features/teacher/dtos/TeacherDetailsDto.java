package com.theeduconnect.exeeduconnectbe.features.teacher.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDetailsDto {
    private Integer id;
    private String fullname;
    private String dateofbirth;
    private String avatarurl;
    private String email;
    private String occupation;
    private String phone;

    private String school;

    private String specialty;

    private String bio;
}
