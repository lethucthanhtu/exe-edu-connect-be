package com.theeduconnect.exeeduconnectbe.features.certificate.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CertificateServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}
