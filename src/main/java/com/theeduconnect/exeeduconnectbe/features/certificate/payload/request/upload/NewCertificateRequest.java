package com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.upload;

import com.theeduconnect.exeeduconnectbe.constants.certificate.validation.CertificateValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.certificate.validation.CertificateValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCertificateRequest {
    @Schema(
            name = "name",
            example = "Third Prize in International Student Reward",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(
            min = CertificateValidationSpecifications.MIN_CERTIFICATE_NAME_LENGTH,
            max = CertificateValidationSpecifications.MAX_CERTIFICATE_NAME_LENGTH,
            message = CertificateValidationMessages.INVALID_CERTIFICATE_NAME)
    private String name;

    @Schema(
            name = "url",
            example = "https://firebase.com/abcxyz",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(
            regexp = CertificateValidationSpecifications.FIREBASE_URL_REGEX,
            message = CertificateValidationMessages.INVALID_CERTIFICATE_URL)
    @Column(name = "url", length = Integer.MAX_VALUE)
    private String url;
}
