package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_inquiry_requests")
public class StudentInquiryRequest {
    @EmbeddedId
    private StudentInquiryRequestId id;

    @MapsId("studentinquiryid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentinquiryid", nullable = false)
    private StudentInquiry studentinquiryid;

    @MapsId("teacherid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacherid", nullable = false)
    private Teacher teacherid;

    @Size(max = 255)
    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer status;

}