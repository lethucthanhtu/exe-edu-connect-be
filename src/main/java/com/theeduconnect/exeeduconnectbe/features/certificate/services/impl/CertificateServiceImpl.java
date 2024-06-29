package com.theeduconnect.exeeduconnectbe.features.certificate.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CertificateMapper;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.CertificateListRequest;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.response.CertificateServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.certificate.services.CertificateService;
import com.theeduconnect.exeeduconnectbe.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final TeacherRepository teacherRepository;
    private final CertificateMapper certificateMapper;
    private final CertificateRepository certificateRepository;
    private UploadCertificateServiceImpl uploadCertificateServiceImpl;

    public CertificateServiceImpl(
            TeacherRepository teacherRepository,
            CertificateMapper certificateMapper,
            CertificateRepository certificateRepository) {
        this.teacherRepository = teacherRepository;
        this.certificateMapper = certificateMapper;
        this.certificateRepository = certificateRepository;
        InitializeChildServices();
    }

    @Override
    public CertificateServiceResponse upload(CertificateListRequest request) {
        return uploadCertificateServiceImpl.Handle(request);
    }

    private void InitializeChildServices() {
        uploadCertificateServiceImpl =
                new UploadCertificateServiceImpl(
                        certificateRepository, teacherRepository, certificateMapper);
    }
}
