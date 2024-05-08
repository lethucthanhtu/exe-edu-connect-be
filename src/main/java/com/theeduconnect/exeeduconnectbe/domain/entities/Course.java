package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseid", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "startdate")
    private LocalDate startdate;

    @Column(name = "enddate")
    private LocalDate enddate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacherid", nullable = false)
    private Teacher teacherid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coursecategoryid", nullable = false)
    private CourseCategory coursecategoryid;

    @OneToMany(mappedBy = "courseid")
    private Set<com.theeduconnect.exeeduconnectbe.domain.entities.AttendingCourse> attendingCourses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "courseid")
    private Set<CourseSchedule> courseSchedules = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "courseid"),
            inverseJoinColumns = @JoinColumn(name = "studentid"))
    private Set<Student> students = new LinkedHashSet<>();

}