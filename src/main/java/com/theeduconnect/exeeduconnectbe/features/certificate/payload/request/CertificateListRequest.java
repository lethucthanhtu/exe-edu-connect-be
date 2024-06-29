package com.theeduconnect.exeeduconnectbe.features.certificate.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateListRequest {
    @JsonIgnore private int teacherid;

    @Valid private List<NewCertificateRequest> newcertificaterequestlist;
}
