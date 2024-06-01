package com.theeduconnect.exeeduconnectbe.features.certificate.payload.request.upload;

import com.theeduconnect.exeeduconnectbe.constants.certificate.validation.CertificateValidationMessages;
import com.theeduconnect.exeeduconnectbe.constants.certificate.validation.CertificateValidationSpecifications;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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

    @NotNull(message = CertificateValidationMessages.INVALID_CERTIFICATE_FILE) private MultipartFile file;
}
