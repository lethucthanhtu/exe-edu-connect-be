package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

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
    private User user;

    @OneToMany(mappedBy = "student")
    private Set<AttendingCourse> attendingCourses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<StudentInquiry> studentInquiries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Like> likes = new LinkedHashSet<>();
}
