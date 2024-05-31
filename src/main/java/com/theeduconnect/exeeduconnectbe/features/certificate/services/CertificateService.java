package com.theeduconnect.exeeduconnectbe.features.certificate.services;

import com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.upload.CertificateListRequest;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.response.CertificateServiceResponse;

public interface CertificateService {
    CertificateServiceResponse upload(CertificateListRequest request);
}
