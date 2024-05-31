package com.theeduconnect.exeeduconnectbe.features.certificate.controllers;

import com.theeduconnect.exeeduconnectbe.constants.certificate.CertificateEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.upload.CertificateListRequest;
import com.theeduconnect.exeeduconnectbe.features.certificate.payload.response.CertificateServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.certificate.services.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CertificateController {

    private final CertificateService certificateService;
    private final JwtService jwtService;

    @Autowired
    public CertificateController(CertificateService certificateService, JwtService jwtService) {
        this.certificateService = certificateService;
        this.jwtService = jwtService;
    }

    @PostMapping(CertificateEndpoints.UPLOAD_CERTIFICATES_URL)
    @Operation(summary = "Allows a teacher to upload his/her certificates.")
    public ResponseEntity<CertificateServiceResponse> UploadCertificates(
            @RequestHeader("Authorization") String rawJwtToken,
            @Valid @RequestBody CertificateListRequest request) {
        int teacherId = jwtService.extractUserId(rawJwtToken);
        request.setTeacherId(teacherId);
        CertificateServiceResponse response = certificateService.upload(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
