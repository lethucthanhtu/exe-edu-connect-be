package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "studentid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentid", nullable = false)
    private User users;

    @OneToMany(mappedBy = "studentid")
    private Set<com.theeduconnect.exeeduconnectbe.domain.entities.AttendingCourse> attendingCourses = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "studentid")
    private Set<com.theeduconnect.exeeduconnectbe.domain.entities.Course> courses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "studentid")
    private Set<StudentInquiry> studentInquiries = new LinkedHashSet<>();

}