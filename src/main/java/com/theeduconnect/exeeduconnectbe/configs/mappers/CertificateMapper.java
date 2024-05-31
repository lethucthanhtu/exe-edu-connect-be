package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.Certificate;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.upload.NewCertificateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    Certificate NewCertificateRequestToCertificateEntity(NewCertificateRequest request);
}
