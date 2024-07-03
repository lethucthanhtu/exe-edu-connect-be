package com.theeduconnect.exeeduconnectbe.features.teacherProfile.services.impl;

import com.theeduconnect.exeeduconnectbe.features.teacherProfile.dtos.UpdateTeacherProfileDto;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.payload.response.TeacherProfileServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.services.TeacherProfileService;
import com.theeduconnect.exeeduconnectbe.repositories.CertificateRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherProfileServiceImpl implements TeacherProfileService {
    private UpdateTeacherProfileServiceImpl updateTeacherProfileServiceImpl;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final CertificateRepository certificateRepository;

    public TeacherProfileServiceImpl(UserRepository userRepository,
                                     TeacherRepository teacherRepository,
                                     CertificateRepository certificateRepository){
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.certificateRepository = certificateRepository;
        InitializeChildServices();
    }
    @Override
    public TeacherProfileServiceResponse update(UpdateTeacherProfileDto dto) {
        return updateTeacherProfileServiceImpl.Handle(dto);
    }
    private void InitializeChildServices(){
        updateTeacherProfileServiceImpl = new UpdateTeacherProfileServiceImpl(
                userRepository,
                teacherRepository,
                certificateRepository
        );
    }
}
