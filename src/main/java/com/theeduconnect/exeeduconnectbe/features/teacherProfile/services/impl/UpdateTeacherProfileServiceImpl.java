package com.theeduconnect.exeeduconnectbe.features.teacherProfile.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.teacherProfile.TeacherProfileServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.teacherProfile.TeacherProfileServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Certificate;
import com.theeduconnect.exeeduconnectbe.domain.Teacher;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.dtos.ConstraintViolationDto;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.dtos.UpdateTeacherProfileDto;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.payload.response.TeacherProfileServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CertificateRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import com.theeduconnect.exeeduconnectbe.utils.FirebaseUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class UpdateTeacherProfileServiceImpl {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final CertificateRepository certificateRepository;
    private UpdateTeacherProfileDto dto;
    Set<ConstraintViolation<UpdateTeacherProfileDto>> violations;
    private List<ConstraintViolationDto> constraintViolationDtoList;
    private User user;
    private Teacher teacher;

    public UpdateTeacherProfileServiceImpl(
            UserRepository userRepository,
            TeacherRepository teacherRepository,
            CertificateRepository certificateRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.certificateRepository = certificateRepository;
    }

    public TeacherProfileServiceResponse Handle(UpdateTeacherProfileDto dto) {
        try {
            this.dto = dto;
            if (!IsDtoValid()) return InvalidDtoResult();
            UpdateFieldsInUserTable();
            UpdateFieldsInTeacherTable();
            UploadCertificates();
            return UpdateTeacherProfileSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean IsDtoValid() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validator.validate(dto);
        if (violations.isEmpty()) return true;
        ConvertConstraintViolationSetToConstraintViolationDtoList();
        return false;
    }
    private void ConvertConstraintViolationSetToConstraintViolationDtoList(){
        constraintViolationDtoList = new ArrayList<>();
        for(ConstraintViolation<UpdateTeacherProfileDto> constraintViolation:violations){
            constraintViolationDtoList.add(new ConstraintViolationDto(
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getMessage()
            ));
        }
    }

    private void UpdateFieldsInUserTable() {
        user = userRepository.findById(dto.getTeacherId()).get();
        user.setFullname(dto.getFullname());
        user.setDateofbirth(dto.getDateofbirth());
        user.setAddress(dto.getAddress());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        userRepository.save(user);
    }
    private void UpdateFieldsInTeacherTable(){
        teacher = teacherRepository.findById(dto.getTeacherId()).get();
        teacher.setOccupation(dto.getOccupation());
        teacher.setSchool(dto.getSchool());
        teacher.setSpecialty(dto.getSpecialization());
        teacherRepository.save(teacher);
    }
    private void UploadCertificates()throws IOException {
        MultipartFile[] certificateFiles = dto.getCertificates();
        for(MultipartFile certificateFile:certificateFiles){
            String certificateUrl =
                    FirebaseUtils.UploadFileToFirebase(certificateFile);
            Certificate certificate = BuildCertificate(certificateFile.getName(),certificateUrl);
            certificateRepository.save(certificate);
        }
    }
    private Certificate BuildCertificate(String name, String url){
        Certificate certificate = new Certificate();
        certificate.setName(name);
        certificate.setUrl(url);
        certificate.setTeacher(teacher);
        return certificate;
    }
    private TeacherProfileServiceResponse UpdateTeacherProfileSuccessfulResult() {
        return new TeacherProfileServiceResponse(
                TeacherProfileServiceHttpResponseCodes.UPDATE_PROFILE_SUCCESS,
                TeacherProfileServiceMessages.UPDATE_PROFILE_SUCCESS,
                null
        );
    }

    private TeacherProfileServiceResponse InvalidDtoResult() {
        return new TeacherProfileServiceResponse(
                TeacherProfileServiceHttpResponseCodes.INVALID_DTO,
                TeacherProfileServiceMessages.INVALID_DTO,
                constraintViolationDtoList
        );
    }

    private TeacherProfileServiceResponse InternalServerErrorResult(Exception e) {
        return new TeacherProfileServiceResponse(
                TeacherProfileServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                TeacherProfileServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
    }
}
