package com.theeduconnect.exeeduconnectbe.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attending_courses")
public class AttendingCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendingcourseid", nullable = false)
    private Integer id;

    @Column(name = "status")
    private Boolean status;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentid", nullable = false)
    private Student student;

    @OneToOne(mappedBy = "attendingcourse")
    private CourseFeedback courseFeedback;
}
