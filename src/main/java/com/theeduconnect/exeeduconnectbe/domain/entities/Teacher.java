package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @Column(name = "teacherid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacherid", nullable = false)
    private User users;

    @Size(max = 255)
    @Column(name = "occupation")
    private String occupation;

    @Size(max = 255)
    @Column(name = "school")
    private String school;

    @Size(max = 255)
    @Column(name = "specialty")
    private String specialty;

    @Size(max = 255)
    @Column(name = "bio")
    private String bio;

    @OneToMany(mappedBy = "teacherid")
    private Set<com.theeduconnect.exeeduconnectbe.domain.entities.Course> courses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "teacher")
    private Set<StudentInquiryRequest> studentInquiryRequests = new LinkedHashSet<>();

}