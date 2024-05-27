package com.theeduconnect.exeeduconnectbe.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_inquiries")
public class StudentInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentinquiryid", nullable = false)
    private Integer id;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentid", nullable = false)
    private Student student;

    @Column(name = "\"Date\"")
    private LocalDate date;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;

    @Size(max = 255)
    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "studentinquiry")
    private Set<StudentInquiryRequest> studentInquiryRequests = new LinkedHashSet<>();
}
