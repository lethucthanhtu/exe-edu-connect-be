package com.theeduconnect.exeeduconnectbe.features.teacherProfile.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConstraintViolationDto {
    private String fieldName;
    private String details;
}
