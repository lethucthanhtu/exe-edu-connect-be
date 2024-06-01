package com.theeduconnect.exeeduconnectbe.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course_schedules")
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coursescheduleid", nullable = false)
    private Integer id;

    @Column(name = "starttime")
    private Instant starttime;

    @Column(name = "duration")
    private Integer duration;

    @Size(max = 255)
    @Column(name = "meeturl")
    private String meeturl;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentid")
    private Student student;

    @OneToMany(mappedBy = "courseschedule")
    private Set<StudentEvaluation> studentEvaluations = new LinkedHashSet<>();
}
