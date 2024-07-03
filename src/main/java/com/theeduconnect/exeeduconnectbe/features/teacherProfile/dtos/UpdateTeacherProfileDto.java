package com.theeduconnect.exeeduconnectbe.features.teacherProfile.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theeduconnect.exeeduconnectbe.constants.teacherProfile.validation.TeacherProfileValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.teacherProfile.validation.TeacherProfileValidationSpecifications;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateTeacherProfileDto {
    @JsonIgnore
    private int teacherId;
    @NotBlank(message = TeacherProfileValidationMessages.FULL_NAME_NOT_EMPTY)
    private String fullname;
    @NotNull(message = TeacherProfileValidationMessages.DATE_OF_BIRTH_VALID)
    private LocalDate dateofbirth;
    @NotBlank(message = TeacherProfileValidationMessages.SEX_NOT_EMPTY)
    private String sex;
    @NotBlank(message = TeacherProfileValidationMessages.ADDRESS_NOT_EMPTY)
    private String address;
    @NotBlank(message = TeacherProfileValidationMessages.EMAIL_VALID)
    @Pattern(regexp = TeacherProfileValidationSpecifications.VALID_EMAIL, message = TeacherProfileValidationMessages.EMAIL_VALID)
    private String email;
    @NotBlank(message = TeacherProfileValidationMessages.PHONE_VALID)
    @Pattern(regexp = TeacherProfileValidationSpecifications.VALID_PHONE, message = TeacherProfileValidationMessages.PHONE_VALID)

    private String phone;

    @NotBlank(message = TeacherProfileValidationMessages.OCCUPATION_NOT_EMPTY)
    private String occupation;

    @NotBlank(message = TeacherProfileValidationMessages.SCHOOL_NOT_EMPTY)
    private String school;

    @NotBlank(message = TeacherProfileValidationMessages.SPECIALIZATION_NOT_EMPTY)

    private String specialization;
    @JsonIgnore
    private MultipartFile cardphoto;
    @JsonIgnore
    private MultipartFile nationalid;
    @JsonIgnore
    private MultipartFile cv;
    @JsonIgnore
    private MultipartFile[] certificates;
}
