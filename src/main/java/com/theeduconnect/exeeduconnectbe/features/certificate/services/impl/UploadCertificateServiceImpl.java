package com.theeduconnect.exeeduconnectbe.features.certificate.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CertificateMapper;
import com.theeduconnect.exeeduconnectbe.constants.certificate.CertificateFileExtensions;
import com.theeduconnect.exeeduconnectbe.constants.certificate.CertificateServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.certificate.CertificateServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Certificate;
import com.theeduconnect.exeeduconnectbe.domain.Teacher;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.upload.CertificateListRequest;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.upload.NewCertificateRequest;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.response.CertificateServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CertificateRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import com.theeduconnect.exeeduconnectbe.utils.FirebaseUtils;
import com.theeduconnect.exeeduconnectbe.utils.MultipartFileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadCertificateServiceImpl {
    private CertificateListRequest request;
    private final CertificateRepository certificateRepository;
    private final TeacherRepository teacherRepository;
    private final CertificateMapper certificateMapper;
    private List<Certificate> certificateList;

    public UploadCertificateServiceImpl(
            CertificateRepository certificateRepository,
            TeacherRepository teacherRepository,
            CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.teacherRepository = teacherRepository;
        this.certificateMapper = certificateMapper;
    }

    public CertificateServiceResponse Handle(CertificateListRequest request) {
        try {
            this.request = request;
            if (!AreCertificateFilesValid()) return InvalidFileResult();
            MapCertificateListRequestToCertificateList();
            certificateRepository.saveAll(certificateList);
            return UploadCertificatesSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean AreCertificateFilesValid() {
        List<NewCertificateRequest> newCertificateRequestList =
                request.getNewCertificateRequestList();
        for (NewCertificateRequest certificateRequest : newCertificateRequestList) {
            if (!MultipartFileUtils.DoesFileMatchExtensions(
                    certificateRequest.getFile(), CertificateFileExtensions.ALLOWED_EXTENSIONS))
                return false;
        }
        return true;
    }

    private void MapCertificateListRequestToCertificateList() throws IOException {
        certificateList = new ArrayList<>();
        List<NewCertificateRequest> newCertificateRequestList =
                request.getNewCertificateRequestList();
        for (NewCertificateRequest newCertificateRequest : newCertificateRequestList) {
            Certificate certificate =
                    certificateMapper.NewCertificateRequestToCertificateEntity(
                            newCertificateRequest);
            String certificateUrl =
                    FirebaseUtils.UploadFileToFirebase(newCertificateRequest.getFile());
            certificate.setUrl(certificateUrl);
            certificate.setTeacher(GetTeacherById());
            certificateList.add(certificate);
        }
    }

    private Teacher GetTeacherById() {
        return teacherRepository.findById(request.getTeacherId()).get();
    }

    private CertificateServiceResponse InvalidFileResult() {
        return new CertificateServiceResponse(
                CertificateServiceHttpResponseCodes.INVALID_CERTIFICATE_FILE,
                CertificateServiceMessages.INVALID_CERTIFICATE_FILE,
                null);
    }

    private CertificateServiceResponse UploadCertificatesSuccessfulResult() {
        return new CertificateServiceResponse(
                CertificateServiceHttpResponseCodes.UPLOAD_CERTIFICATES_SUCCESSFUL,
                CertificateServiceMessages.UPLOAD_CERTIFICATES_SUCCESSFUL,
                null);
    }

    private CertificateServiceResponse InternalServerErrorResult(Exception e) {
        return new CertificateServiceResponse(
                CertificateServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CertificateServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
